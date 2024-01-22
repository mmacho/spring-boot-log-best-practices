package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.example.demo.configuration.api.SecurityConfiguration;
import com.example.demo.configuration.api.TraceConfiguration;
import com.example.demo.configuration.api.WebConfigurer;

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
}
