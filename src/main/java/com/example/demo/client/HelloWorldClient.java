package com.example.demo.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.example.demo.webservice.HelloWorldWS;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HelloWorldClient {

	private HelloWorldWS helloWorldRequesterBean;

	public HelloWorldClient(@Qualifier("helloWorldRequesterBean") HelloWorldWS helloWorldRequesterBean) {
		this.helloWorldRequesterBean = helloWorldRequesterBean;
	}

	public String callHelloWorld(String name) {
		log.info("Handling request {}", name);
		String message = helloWorldRequesterBean.createMessage(name);
		log.info("Handling response {}", message);
		return message;
	}

	public HelloWorldWS getHelloWorldWS() {
		return helloWorldRequesterBean;
	}

}
