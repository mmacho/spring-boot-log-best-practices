package com.example.demo.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.example.demo.configuration.domain.AsyncConfiguration;
import com.example.demo.configuration.domain.SchedulerConfig;

@Configuration
@ComponentScan(basePackages = { "com.example.demo.service", "com.example.demo.scheduler", "com.example.demo.client" })
@Import({ SchedulerConfig.class, AsyncConfiguration.class, QuartzConfiguration.class })
// , AopConfiguration.class
public class DomainConfiguration {

}
