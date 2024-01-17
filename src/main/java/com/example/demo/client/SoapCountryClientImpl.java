package com.example.demo.client;

import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import io.spring.guides.gs_producing_web_service.GetCountryRequest;
import io.spring.guides.gs_producing_web_service.GetCountryResponse;
import io.spring.guides.gs_producing_web_service.ObjectFactory;

@Component
public class SoapCountryClientImpl {

	private WebServiceTemplate webServiceTemplate;

	private ObjectFactory factory;

	public SoapCountryClientImpl(WebServiceTemplate webServiceTemplate) {
		this.webServiceTemplate = webServiceTemplate;
		this.factory = new ObjectFactory();
	}

	public GetCountryResponse getCountry(String name) {
		GetCountryRequest request = map(name);
		return (GetCountryResponse) webServiceTemplate.marshalSendAndReceive(request);
	}

	private GetCountryRequest map(String name) {
		GetCountryRequest request = factory.createGetCountryRequest();
		request.setName(name);
		return request;
	}
}
