package com.example.demo.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.websocket.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.websocket.ClientWebSocketContainer;
import org.springframework.integration.websocket.IntegrationWebSocketContainer;
import org.springframework.integration.websocket.ServerWebSocketContainer;
import org.springframework.integration.websocket.WebSocketListener;
import org.springframework.integration.websocket.inbound.WebSocketInboundChannelAdapter;
import org.springframework.integration.websocket.support.SubProtocolHandlerRegistry;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.example.demo.websocket.ClientWebSocketIntegrationHandler;

// spring sleuth only works with spring integration websocket
@Configuration
@EnableIntegration
@IntegrationComponentScan(basePackages = "com.example.demo.controller")
@ComponentScan(basePackages = "com.example.demo.websocket")
public class WebSocketIntegrationConfig {

	WebSocketListener serverWebSocketIntegrationListener;

	public WebSocketIntegrationConfig(WebSocketListener serverWebSocketIntegrationListener) {
		this.serverWebSocketIntegrationListener = serverWebSocketIntegrationListener;
	}

	// Server side
	@Bean
	IntegrationWebSocketContainer serverWebSocketContainer() {
		ServerWebSocketContainer serverWebSocketContainer = new ServerWebSocketContainer("/messages");
		serverWebSocketContainer.setAllowedOrigins("*");
		serverWebSocketContainer.setMessageListener(serverWebSocketIntegrationListener);
		serverWebSocketContainer.setHandshakeHandler(handshakeIntegrationHandler());
		return serverWebSocketContainer;
	}

	@Bean
	HandshakeHandler handshakeIntegrationHandler() {
		return new DefaultHandshakeHandler();
	}

	@Configuration
	public static class ClientWebSocketIntegrationConfig {

		private static final String URL = "ws://localhost:8081/messages";

		ClientWebSocketIntegrationHandler clientWebSocketIntegrationHandler;

		public ClientWebSocketIntegrationConfig(ClientWebSocketIntegrationHandler clientWebSocketHandler) {
			this.clientWebSocketIntegrationHandler = clientWebSocketHandler;
		}

		@Bean
		WebSocketClient webSocketIntegrationClient() {
			StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
			Map<String, Object> userProperties = new HashMap<>();
			userProperties.put(Constants.IO_TIMEOUT_MS_PROPERTY, "" + (Constants.IO_TIMEOUT_MS_DEFAULT * 6));
			webSocketClient.setUserProperties(userProperties);
			return webSocketClient;
		}

		@Bean
		IntegrationWebSocketContainer clientWebSocketContainer() {
			return new ClientWebSocketContainer(webSocketIntegrationClient(), URL);
		}

		@Bean
		MessageChannel webSocketInboundChannel() {
			return new DirectChannel();
		}

		@Bean
		MessageProducer webSocketInboundChannelAdapter() {
			WebSocketInboundChannelAdapter webSocketInboundChannelAdapter = new WebSocketInboundChannelAdapter(
					clientWebSocketContainer(), new SubProtocolHandlerRegistry(clientWebSocketIntegrationHandler));
			webSocketInboundChannelAdapter.setOutputChannel(webSocketInboundChannel());
			return webSocketInboundChannelAdapter;
		}

	}

}
