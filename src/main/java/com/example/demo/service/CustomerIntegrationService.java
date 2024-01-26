package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.client.CustomerClient;
import com.example.demo.controller.customer.CustomerResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;

// https://funcionaenmimaquina.com/guia-minima-de-resilience4j-y-circuit-breaker-en-spring-boot/
@Service
@Slf4j
public class CustomerIntegrationService {

	private final CustomerClient customerClient;

	public CustomerIntegrationService(CustomerClient customerClient) {
		this.customerClient = customerClient;
	}

	@CircuitBreaker(name = "default", fallbackMethod = "executeFallBack")
	@TimeLimiter(name = "default")
	public CustomerResponse execute(Long id) {
		return customerClient.findById(id);
	}

	public CustomerResponse executeFallBack() {
		log.info("Entrando a tolerancia a fallos{}");
		return new CustomerResponse("dummy", "dummy", "dummy");
	}
}
