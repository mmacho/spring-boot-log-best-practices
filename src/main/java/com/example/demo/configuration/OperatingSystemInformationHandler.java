package com.example.demo.configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OperatingSystemInformationHandler extends InformationHandler {

	protected OperatingSystemInformationHandler(String applicationName, String buildVersion, String buildTimestamp,
			String profile) {

		super(applicationName, buildVersion, buildTimestamp, profile, log);
	}

}
