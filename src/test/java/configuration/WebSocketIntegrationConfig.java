package configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.websocket.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.websocket.ClientWebSocketContainer;
import org.springframework.integration.websocket.IntegrationWebSocketContainer;
import org.springframework.integration.websocket.ServerWebSocketContainer;
import org.springframework.integration.websocket.WebSocketListener;
import org.springframework.integration.websocket.inbound.WebSocketInboundChannelAdapter;
import org.springframework.integration.websocket.outbound.WebSocketOutboundMessageHandler;
import org.springframework.integration.websocket.support.SubProtocolHandlerRegistry;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.example.demo.websocket.ClientWebSocketIntegrationHandler;

// Hay cosas que no son necesarias para que funcione ws esta mezclado con  | MessagingGateway, Gateway, ServiceActivator|
@Configuration
@EnableIntegration
@IntegrationComponentScan(basePackages = "com.example.demo.controller")
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

	@Bean
	@ServiceActivator(inputChannel = "integration.gateway.channel")
	MessageHandler webSocketOutboundMessageHandler() {
		return new WebSocketOutboundMessageHandler(serverWebSocketContainer());
	}

	@Bean(name = "integration.gateway.channel")
	MessageChannel messageChannel() {
		return new DirectChannel();
	}

	@Configuration
	public class ClientWebSocketIntegrationConfig {

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
