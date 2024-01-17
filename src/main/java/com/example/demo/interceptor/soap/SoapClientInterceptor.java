package com.example.demo.interceptor.soap;

import java.io.IOException;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

public class SoapClientInterceptor implements ClientInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(SoapClientInterceptor.class);

	@Override
	public boolean handleRequest(MessageContext messageContext) {
		return true;
	}

	@Override
	public boolean handleResponse(MessageContext messageContext) {
		return true;
	}

	@Override
	public boolean handleFault(MessageContext messageContext) {
		try {
			System.out.println("Request :");
			messageContext.getRequest().writeTo(System.out);
			System.out.println("\nResponse : ");
			messageContext.getResponse().writeTo(System.out);
			System.out.println();
		} catch (IOException ignored) {
		}
		WebServiceMessage message = messageContext.getResponse();
		SaajSoapMessage saajSoapMessage = (SaajSoapMessage) message;
		SOAPMessage soapMessage = saajSoapMessage.getSaajMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();
		try {
			SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
			SOAPBody soapBody = soapEnvelope.getBody();
			SOAPFault soapFault = soapBody.getFault();
			throw new RuntimeException(
					String.format("Error occurred while invoking web service - %s ", soapFault.getFaultString()));
		} catch (SOAPException e) {
			logger.error(e.getMessage(), e);
		}
		return true;
	}

	@Override
	public void afterCompletion(MessageContext messageContext, Exception ex) {
	}
}
