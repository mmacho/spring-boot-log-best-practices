package com.example.demo.configuration;

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

}
