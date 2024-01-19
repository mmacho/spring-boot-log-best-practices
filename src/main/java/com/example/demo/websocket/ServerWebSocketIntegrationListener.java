package com.example.demo.websocket;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.demo.websocket.message.HelloRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ServerWebSocketIntegrationListener extends LogWebSocket {

	private final ObjectMapper objectMapper;

	// sessions bl should go to service and repository
	private Map<String, WebSocketSession> sessions;

	public ServerWebSocketIntegrationListener(final ObjectMapper objectMapper) {
		super(objectMapper);
		this.sessions = new ConcurrentHashMap<>();
		this.objectMapper = objectMapper;
	}

	@Override
	public List<String> getSubProtocols() {
		// return Collections.singletonList("subprotocol.demo.websocket");
		return Arrays.asList("SHOULD");
	}

	@Override
	public void onMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		message.getPayload();
	}

	@Override
	public void afterSessionStarted(WebSocketSession session) throws Exception {
		sessions.put(session.getId(), session);

	}

	@Override
	public void afterSessionEnded(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		sessions.remove(session.getId());

	}

	// to test
	// @Scheduled(fixedDelay = 60000)
	void sendPeriodicMessages() {
		sessions.forEach((sessionId, session) -> {
			try {
				sendMessage(session);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		});

	}

	private void sendMessage(WebSocketSession session) throws Exception {
		if (session.isOpen()) {
			HelloRequest r = new HelloRequest();
			r.setMessage("The response");
			TextMessage message = new TextMessage(objectMapper.writeValueAsString(r));
			session.sendMessage(message);
			log.info("Sended hello request message to sessionId {}", session.getId());
		}
	}

}
