package com.example.demo.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DemoServiceImpl implements DemoService {

	private final RestTemplate restTemplate;

	DemoServiceImpl(final RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String execute(String authToken) {
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

		return result + resultNew;
	}

	@Override
	public String getWelcomeMessage() {
		return "Hello World!";
	}
}
