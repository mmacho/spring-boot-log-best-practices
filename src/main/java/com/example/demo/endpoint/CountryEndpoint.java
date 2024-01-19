package com.example.demo.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.demo.client.HelloWorldClient;

import io.spring.guides.gs_producing_web_service.Country;
import io.spring.guides.gs_producing_web_service.GetCountryRequest;
import io.spring.guides.gs_producing_web_service.GetCountryResponse;

@Endpoint
public class CountryEndpoint {

	private static final Logger log = LoggerFactory.getLogger(CountryEndpoint.class);

	public static final String NAMESPACE = "http://spring.io/guides/gs-producing-web-service";

	public static final String COUNTRY_REQUEST_LOCAL_PART = "getCountryRequest";

	private final CountryRepository countryRepository;

	private final HelloWorldClient helloWorldClient;

	public CountryEndpoint(CountryRepository countryRepository, HelloWorldClient helloWorldClient) {
		this.countryRepository = countryRepository;
		this.helloWorldClient = helloWorldClient;
	}

	@PayloadRoot(namespace = NAMESPACE, localPart = COUNTRY_REQUEST_LOCAL_PART)
	@ResponsePayload
	public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) throws CountryNotFoundException {
		log.info("Handling request {}", request.getName());
		GetCountryResponse response = new GetCountryResponse();
		Country country = countryRepository.findCountry(request.getName());
		if (null == country) {
			throw new CountryNotFoundException("Country with id: " + request.getName() + " was not found.");
		}
		String result = helloWorldClient.callHelloWorld(request.getName());
		log.info("Result {}", result);
		response.setCountry(country);
		log.info("Handling response {}", response.getCountry().getCapital());
		return response;
	}

}
