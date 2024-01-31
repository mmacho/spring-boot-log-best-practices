package com.example.demo.configuration;

import java.util.Arrays;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.example.demo.configuration.api.SecurityConfiguration;
import com.example.demo.configuration.api.TraceConfiguration;
import com.example.demo.configuration.api.WebConfigurer;
import com.example.demo.controller.customer.v1.CustomerControllerV1;
import com.example.demo.controller.customer.v2.CustomerControllerV2;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@ComponentScan(basePackages = { "com.example.demo.controller", "com.example.demo.filter" })
@Import({ SecurityConfiguration.class, SoapConfiguration.class, WebSocketIntegrationConfig.class,
		TraceConfiguration.class, WebConfigurer.class })
public class ApiConfiguration {

	@Value("${info.app.name:unknown}")
	private String applicationName;

	@Value("${info.app.version:unknown}")
	private String buildVersion;

	@Value("${info.app.timestamp:unknown}")
	private String buildTimestamp;

	@Value("${spring.profiles.active:unknown}")
	private String profile;

	@Bean
	OperatingSystemInformationHandler operatingSystemInformationHandler() {
		return new OperatingSystemInformationHandler(applicationName, buildVersion, buildTimestamp, profile);
	}

	@Configuration
	public static class WebConfiguration {

		@Bean
		ObjectMapper objectMapper() {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());

			SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAll();
			SimpleFilterProvider filterProvider = new SimpleFilterProvider();
			filterProvider.setDefaultFilter(filter);
			filterProvider.setFailOnUnknownId(Boolean.FALSE);
			objectMapper.setFilterProvider(filterProvider);

			objectMapper.setDefaultPropertyInclusion(Include.NON_NULL);
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, Boolean.TRUE);
			objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, Boolean.FALSE);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.FALSE);
			return objectMapper;
		}
	}

	@Configuration
	public static class OpenApiConfiguration {

		@Bean
		OpenAPI customOpenAPI() {

			return new OpenAPI()
					.components(new Components().addSecuritySchemes("spring_oauth",
							new SecurityScheme().type(SecurityScheme.Type.OAUTH2).description("Oauth2 flow")
									.flows(new OAuthFlows().password(new OAuthFlow()
											.tokenUrl("http://localhost:8080" + "/tek-api/oauth/token")))))
					.security(Arrays.asList(new SecurityRequirement().addList("spring_oauth")))
					.info(new Info().title("Skeleton Application API").description(
							"This is a sample Spring Boot RESTful service using springdoc-openapi and OpenAPI 3.")
							.termsOfService("terms")
							.contact(new Contact().email("m2m@gmail.com").name("Developer: Manuel Macho"))
							.license(new License().name("GNU")).version("1.0"));
		}

		//https://blog.jdriven.com/2022/10/springdoc-openapi-definitions/

		@Bean
		GroupedOpenApi apiV1() {
			return GroupedOpenApi.builder()
					.group("v1")
					.packagesToScan(CustomerControllerV1.class.getPackageName())
					.build();
		}

		@Bean
		GroupedOpenApi apiV2() {
			return GroupedOpenApi.builder()
					.group("v2")
					.packagesToScan(CustomerControllerV2.class.getPackageName())
					.build();
		}

	}
}
