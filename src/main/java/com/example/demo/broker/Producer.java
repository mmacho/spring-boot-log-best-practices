package com.example.demo.broker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

	private static final String $CLOUDKARAFKA_TOPIC = "${cloudkarafka.topic}";

	private static final Logger log = LoggerFactory.getLogger(Producer.class);

	private final KafkaTemplate<String, String> kafkaTemplate;

	@Value($CLOUDKARAFKA_TOPIC)
	private String topic;

	Producer(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void send(SampleMessage message) {
		this.kafkaTemplate.send(topic, message.getMessage());
		log.info("Sent sample message [" + message + "] to " + topic);
	}

}
