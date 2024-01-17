package com.example.demo.websocket;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SubProtocolHandler;

@Component
public class ClientWebSocketIntegrationHandler implements SubProtocolHandler {

	private static final Logger logger = LoggerFactory.getLogger(ClientWebSocketIntegrationHandler.class);

	public ClientWebSocketIntegrationHandler() {
		super();
	}

	@Override
	public List<String> getSupportedProtocols() {
		return Arrays.asList("SHOULD");
	}

	@Override
	public void handleMessageFromClient(WebSocketSession session, WebSocketMessage<?> message,
			MessageChannel outputChannel) throws Exception {
		String receivedMessage = (String) message.getPayload();
		logger.info("Handling message: {} from sessionId {}", receivedMessage, session.getId());

	}

	@Override
	public void handleMessageToClient(WebSocketSession session, Message<?> message) throws Exception {
		String receivedMessage = (String) message.getPayload();
		logger.info("Handling message: {} from sessionId {}", receivedMessage, session.getId());

	}

	@Override
	public String resolveSessionId(Message<?> message) {
		return SimpMessageHeaderAccessor.getSessionId(message.getHeaders());
	}

	@Override
	public void afterSessionStarted(WebSocketSession session, MessageChannel outputChannel) throws Exception {
		logger.info("Server connection opened with sessionId {}", session.getId());

	}

	@Override
	public void afterSessionEnded(WebSocketSession session, CloseStatus closeStatus, MessageChannel outputChannel)
			throws Exception {
		logger.info("Server connection with sessionId {} closed because of: {}", session.getId(),
				closeStatus.getReason());
	}

}
