package com.example.demo.broker;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

	private static final String $_CLOUDKARAFKA_TOPIC = "${cloudkarafka.topic}";

	private static final Logger log = LoggerFactory.getLogger(Consumer.class);

	@KafkaListener(topics = $_CLOUDKARAFKA_TOPIC)
	public void processMessage(String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
			@Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics, @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
		log.info("{} - {} - {} - {}", topics.get(0), partitions.get(0), offsets.get(0), message);
	}
}
