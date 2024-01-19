package com.example.demo.webservice;

import javax.xml.ws.BindingProvider;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;

import com.example.demo.BaseTest;
import com.example.demo.client.HelloWorldClient;

import app.DemoApplication;

@ContextConfiguration(classes = { DemoApplication.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HelloWorldWSConsumerServiceIT extends BaseTest {

	@LocalServerPort
	private int port;

	private String url;

	@Autowired
	private HelloWorldClient helloWorldClient;

	@BeforeEach
	public void setUp() {
		url = String.format("http://localhost:%d/soap/helloWorldWS", port);
	}

	@Test
	void testCallHelloWorld() {
		BindingProvider bindingProvider = (BindingProvider) helloWorldClient.getHelloWorldWS();
		bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
		Assertions.assertEquals("Hello General Kenobi", helloWorldClient.callHelloWorld("General Kenobi"));
	}
}
