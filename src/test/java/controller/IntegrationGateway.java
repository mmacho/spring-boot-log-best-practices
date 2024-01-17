package controller;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface IntegrationGateway {

	@Gateway(requestChannel = "integration.gateway.channel")
	String sendMessage(String payload);

}
