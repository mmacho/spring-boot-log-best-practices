package com.example.demo.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.demo.broker.Producer;
import com.example.demo.broker.SampleMessage;

@Component
public class DemoScheduler {

	private static final Logger log = LoggerFactory.getLogger(DemoScheduler.class);

	private final Producer producer;

	public DemoScheduler(final Producer producer) {
		this.producer = producer;
	}

	// to test
	// @Scheduled(fixedDelay = 120000)
	public void run() {
		log.info("DemoScheduler start");
		for (int i = 0; i < 1; i++) {
			producer.send(new SampleMessage(i, "A simple test message"));
		}
		log.info("DemoScheduler finished");
	}
}
