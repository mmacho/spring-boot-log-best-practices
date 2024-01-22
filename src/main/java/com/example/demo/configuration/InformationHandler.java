package com.example.demo.configuration;

import org.slf4j.Logger;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

public abstract class InformationHandler {

	private final Logger log;

	private final String applicationName;

	private final String buildVersion;

	private final String buildTimestamp;

	private final String profile;

	protected InformationHandler(final String applicationName, final String buildVersion, final String buildTimestamp,
			final String profile, final Logger log) {

		this.applicationName = applicationName;
		this.buildVersion = buildVersion;
		this.buildTimestamp = buildTimestamp;
		this.profile = profile;
		this.log = log;
	}

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {

		log.info("Application {} ---- buildVersion: {} | buildTimestamp: {} | environment {}", applicationName,
				buildVersion, buildTimestamp, profile);
	}

}
