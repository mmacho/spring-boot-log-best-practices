package com.example.demo.controller;

import static net.logstash.logback.argument.StructuredArguments.v;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DemoController {

	private static final Logger log = LoggerFactory.getLogger(DemoController.class);

	private final RestTemplate restTemplate;

	DemoController(final RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Value("${APP_NAME}")
	private String appName;

	@GetMapping("/a")
	ResponseEntity<String> a(@RequestHeader(name = "Authorization") String authToken) {

		log.info("Handling a - " + appName);

		// no le veo porque habría que meterlo por todas partes
		log.info("This is a normal log statement: {}", v("foo", "bar"));

		// funciona con restTemplate3 que inyecta el token por interceptor
		// ResponseEntity<String> response1 =
		// restTemplate.getForEntity("http://localhost:8082/b", String.class);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", authToken);

		@SuppressWarnings({ "rawtypes", "unchecked" })
		HttpEntity request = new HttpEntity(headers);

		HttpHeaders headersNew = new HttpHeaders();
		headersNew.setBearerAuth(authToken.split(" ")[1]);

		HttpEntity<String> requestNew = new HttpEntity<>(headersNew);

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8082/b", HttpMethod.GET, request,
				String.class);

		ResponseEntity<String> responseNew = restTemplate.exchange("http://localhost:8082/b", HttpMethod.GET,
				requestNew, String.class);

		String result = response.getBody();
		String resultNew = responseNew.getBody();
		log.info("Reply = " + result + resultNew);

		return ResponseEntity.ok("Hello from /a - " + appName + ", " + result);
	}

	@GetMapping("/b")
	ResponseEntity<String> b(@RequestHeader(name = "Authorization") String authToken) {
		log.info("Handling b - " + appName);
		return ResponseEntity.ok("Hello from /b - " + appName);
	}
}
