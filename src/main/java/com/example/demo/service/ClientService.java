package com.example.demo.service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.demo.websocket.message.HelloRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClientService {

	private final ObjectMapper objectMapper;

	private Map<String, WebSocketSession> sessions;

	public ClientService(final ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
		this.sessions = new ConcurrentHashMap<>();
	}

	public WebSocketSession getByClientId(String clientId) {
		if (!sessions.containsKey(clientId)) {
			throw new NoSuchElementException();
		}
		return sessions.get(clientId);
	}

	public Map<String, WebSocketSession> getAll() {
		return sessions;
	}

	public void save(WebSocketSession session) {
		sessions.put(session.getId(), session);
	}

	public void delete(WebSocketSession session) {
		sessions.remove(session.getId());
	}

	@SneakyThrows
	public void publishToClient(String clientId, String data) {
		if (!sessions.containsKey(clientId)) {
			return;
		}
		WebSocketSession session = sessions.get(clientId);
		if (!session.isOpen()) {
			return;
		}
		HelloRequest r = new HelloRequest();
		r.setMessage("The response");
		TextMessage message = new TextMessage(objectMapper.writeValueAsString(r));
		session.sendMessage(message);
		log.info("Sended hello request message to sessionId {}", session.getId());
	}

}
