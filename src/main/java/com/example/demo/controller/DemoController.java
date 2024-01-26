package com.example.demo.controller;

import static net.logstash.logback.argument.StructuredArguments.v;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.DemoService;

@RestController
public class DemoController {

	private static final Logger log = LoggerFactory.getLogger(DemoController.class);

	private final DemoService demoService;

	DemoController(final DemoService demoService) {
		this.demoService = demoService;
	}

	@Value("${spring.application.name}")
	private String appName;

	@GetMapping("/")
	public @ResponseBody String greeting() {
		return demoService.getWelcomeMessage();
	}

	@GetMapping("/a")
	ResponseEntity<String> a(@RequestHeader(name = "Authorization") String authToken) {
		log.info("Handling a - {}", appName);
		// no le veo porque habría que meterlo por todas partes
		log.info("This is a normal log statement: {}", v("foo", "bar"));
		String result = demoService.execute(authToken);
		log.info("Reply = {}", result);
		return ResponseEntity.ok("Hello from /a - " + appName + ", " + result);
	}

	@GetMapping("/b")
	ResponseEntity<String> b(@RequestHeader(name = "Authorization") String authToken) {
		log.info("Handling b - " + appName);
		return ResponseEntity.ok("Hello from /b - " + appName);
	}
}
