package com.example.demo.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.demo.broker.Producer;
import com.example.demo.broker.SampleMessage;

//@Component
public class DemoScheduler {

	private static final Logger log = LoggerFactory.getLogger(DemoScheduler.class);

	private final Producer producer;

	public DemoScheduler(final Producer producer) {
		this.producer = producer;
	}

	@Scheduled(fixedDelay = 10000)
	public void run() {
		log.info("DemoScheduler start");
		for (int i = 1; i < 2; i++) {
			producer.send(new SampleMessage(i, "A simple test message"));
		}
		log.info("DemoScheduler finished");
	}
}
