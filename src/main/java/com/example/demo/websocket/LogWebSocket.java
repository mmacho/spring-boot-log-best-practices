package com.example.demo.websocket;

import org.springframework.integration.websocket.WebSocketListener;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

// Instead with decorator pattern
@Slf4j
public abstract class LogWebSocket implements WebSocketListener {

	private final ObjectMapper objectMapper;

	protected LogWebSocket(final ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void onMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		log.info("Handling message: {} from sessionId {}",
				objectMapper.readValue((String) message.getPayload(), String.class), session.getId());
	}

	@Override
	public void afterSessionStarted(WebSocketSession session) throws Exception {
		log.info("Server connection opened with sessionId {}", session.getId());
	}

	@Override
	public void afterSessionEnded(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		log.info("Server connection with sessionId {} closed because of: {}", session.getId(), closeStatus.getReason());
	}

}
