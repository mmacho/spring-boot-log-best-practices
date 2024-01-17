package controller;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;

import lombok.extern.slf4j.Slf4j;

@MessageEndpoint
@Slf4j
public class IntegrationService {

	@ServiceActivator(inputChannel = "integration.gateway.channel")
	public void anotherMessage(Message<String> message) throws MessagingException {
		log.info("Message : {}", message);
		MessageChannel replyChannel = (MessageChannel) message.getHeaders().getReplyChannel();
		MessageBuilder.fromMessage(message);
		Message<String> newMessage = MessageBuilder
				.withPayload("Welcome, " + message.getPayload() + " to String Integration").build();
		replyChannel.send(newMessage);
	}
}
