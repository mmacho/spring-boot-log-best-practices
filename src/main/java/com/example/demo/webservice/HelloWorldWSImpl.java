package com.example.demo.webservice;

import javax.jws.WebService;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("helloWorldWS")
@WebService(endpointInterface = "com.example.demo.webservice.HelloWorldWS", serviceName = "helloWorldWS")
public class HelloWorldWSImpl implements HelloWorldWS {

	public HelloWorldWSImpl() {
	}

	@Override
	public String createMessage(String name) {
		log.info("Handling request {}", name);
		String response = "Hello " + name;
		log.info("Handling request {}", response);
		return response;
	}
}
