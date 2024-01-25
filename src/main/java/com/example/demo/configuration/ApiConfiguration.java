package com.example.demo.configuration;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.demo.configuration.api.SecurityConfiguration;
import com.example.demo.configuration.api.TraceConfiguration;
import com.example.demo.configuration.api.WebConfigurer;

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

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration cc = new CorsConfiguration();
		cc.setAllowedHeaders(Arrays.asList("Origin,Accept", "X-Requested-With", "Content-Type",
				"Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization"));
		cc.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		cc.setAllowedOrigins(Arrays.asList("/*"));
		cc.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "PATCH"));
		cc.addAllowedOrigin("*");
		cc.setMaxAge(Duration.ZERO);
		cc.setAllowCredentials(Boolean.TRUE);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", cc);
		return source;
	}

	@Configuration
	public class OpenApiConfiguration {

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

	}
}
