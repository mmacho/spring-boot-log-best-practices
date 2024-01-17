package com.example.demo.websocket;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.websocket.WebSocketListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.demo.websocket.message.HelloRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ServerWebSocketIntegrationListener implements WebSocketListener {

	private static final Logger logger = LoggerFactory.getLogger(ServerWebSocketIntegrationListener.class);

	private final ObjectMapper objectMapper;

	// sessions bl should go to service and repository
	private Map<String, WebSocketSession> sessions;

	public ServerWebSocketIntegrationListener(final ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
		this.sessions = new ConcurrentHashMap<>();

	}

	@Override
	public List<String> getSubProtocols() {
		return Arrays.asList("SHOULD");
	}

	@Override
	public void onMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		String receivedMessage = (String) message.getPayload();
		logger.info("Handling message: {} from sessionId {}", receivedMessage, session.getId());
	}

	@Override
	public void afterSessionStarted(WebSocketSession session) throws Exception {
		logger.info("Server connection opened with sessionId {}", session.getId());
		sessions.put(session.getId(), session);

	}

	@Override
	public void afterSessionEnded(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		logger.info("Server connection with sessionId {} closed because of: {}", session.getId(),
				closeStatus.getReason());
		sessions.remove(session.getId());

	}

	@Scheduled(fixedDelay = 60000)
	void sendPeriodicMessages() {
		sessions.forEach((sessionId, session) -> {
			try {
				sendMessage(session);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		});

	}

	private void sendMessage(WebSocketSession session) throws Exception {
		if (session.isOpen()) {
			HelloRequest r = new HelloRequest();
			r.setMessage("The response");
			TextMessage message = new TextMessage(objectMapper.writeValueAsString(r));
			session.sendMessage(message);
			logger.info("Sended hello request message to sessionId {}", session.getId());
		}
	}

}
