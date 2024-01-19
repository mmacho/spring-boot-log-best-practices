package com.example.demo.interceptor.soap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPMessage;

import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GlobalEndpointInterceptor implements EndpointInterceptor {

	@Override
	public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {
		log.info("Handle CountryEndpoint Request");
		String requestContent = getMessageContent(messageContext.getRequest());
		WebServiceMessage request = messageContext.getRequest();
		log.info("Headers request: {}", getHeaders(request));
		log.info("Received request [" + requestContent + "]");
		return true;
	}

	@Override
	public boolean handleResponse(MessageContext messageContext, Object endpoint) throws Exception {
		log.info("Handle CountryEndpoint Response");
		String responseContent = getMessageContent(messageContext.getResponse());
		WebServiceMessage response = messageContext.getRequest();
		log.info("Headers response: {}", getHeaders(response));
		log.info("Sent response [" + responseContent + "]");
		return true;
	}

	@Override
	public boolean handleFault(MessageContext messageContext, Object endpoint) throws Exception {
		log.info("Handle CountryEndpoint Exceptions");
		String responseContent = getMessageContent(messageContext.getResponse());
		log.info("Fault [" + responseContent + "]");
		return true;
	}

	@Override
	public void afterCompletion(MessageContext messageContext, Object endpoint, Exception ex) throws Exception {
		log.info("Handle CountryEndpoint After Completion");
		WebServiceMessage request = messageContext.getRequest();
		WebServiceMessage response = messageContext.getResponse();
		String requestContent = getMessageContent(request);
		String responseContent = getMessageContent(response);
		log.info("Sent response [" + responseContent + "] for request [" + requestContent + "]");
	}

	private List<String> getHeaders(WebServiceMessage message) {
		SaajSoapMessage saajSoapMessage = (SaajSoapMessage) message;
		SOAPMessage soapMessage = saajSoapMessage.getSaajMessage();
		MimeHeaders mimeHeaders = soapMessage.getMimeHeaders();
		return StreamSupport
				.stream(Spliterators.spliteratorUnknownSize(mimeHeaders.getAllHeaders(), Spliterator.ORDERED), false)
				.map(x -> x.getName() + "=" + x.getValue()).collect(Collectors.toList());
	}

	private String getMessageContent(WebServiceMessage message) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		message.writeTo(bos);
		return bos.toString(StandardCharsets.UTF_8);
	}
}
