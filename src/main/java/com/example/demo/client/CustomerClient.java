package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.controller.customer.CustomerResponse;

@FeignClient(value = "customerClient", url = "${customer.client.url:http://localhost:8081/api/v1/customer}")
public interface CustomerClient {

	@GetMapping("/{id}")
	CustomerResponse findById(@PathVariable("id") Long id);
}
