package com.example.demo.configuration.api;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletResponse;

import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// https://docs.spring.io/spring-cloud-sleuth/docs/current/reference/html/howto.html#how-to-add-headers-to-the-http-server-response

@Configuration(proxyBeanMethods = false)
public class TraceConfiguration {

	// Example of a servlet Filter for non-reactive applications
	@Bean
	Filter traceIdInResponseFilter(Tracer tracer) {
		return (request, response, chain) -> {
			Span currentSpan = tracer.currentSpan();
			if (currentSpan != null) {
				HttpServletResponse resp = (HttpServletResponse) response;
				// putting trace id value in [mytraceid] response header
				// https://docs.use.id/docs/logging-request-ids-and-correlation-ids
				// X-Request-ID: A UUDv4 unique to that HTTP request and response combination.
				// --> spanId local
				resp.addHeader("X-Request-ID", currentSpan.context().spanId());
				// X-Correlation-ID: A UUIDv4 unique over a series of requests and responses,
				// identifying a transaction. --> traceId
				resp.addHeader("X-Correlation-ID", currentSpan.context().traceId());
			}
			chain.doFilter(request, response);
		};
	}

}
