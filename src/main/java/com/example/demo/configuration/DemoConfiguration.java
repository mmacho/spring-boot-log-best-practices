package com.example.demo.configuration;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import ch.qos.logback.access.tomcat.LogbackValve;

@Configuration
@Import({ ApiConfiguration.class, ProcessorConfiguration.class, DomainConfiguration.class,
		RepositoryConfiguration.class })
public class DemoConfiguration {

	// https://github.com/jochenchrist/spring-boot-access-logs-demo/blob/master/README.md
	// @Bean
	WebServerFactoryCustomizer<TomcatServletWebServerFactory> accessLogsCustomizer() {
		return factory -> {
			var logbackValve = new LogbackValve();
			logbackValve.setFilename("logback-access.xml");
			logbackValve.setAsyncSupported(true);
			factory.addContextValves(logbackValve);
		};
	}

}
