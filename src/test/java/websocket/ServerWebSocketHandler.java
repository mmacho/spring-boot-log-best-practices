package websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.demo.websocket.message.HelloRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ServerWebSocketHandler extends TextWebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger(ServerWebSocketHandler.class);

	private final ObjectMapper objectMapper;

	private Map<String, WebSocketSession> sessions;

	public ServerWebSocketHandler(final ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
		this.sessions = new ConcurrentHashMap<>();
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("Server connection opened with sessionId {}", session.getId());
		sessions.put(session.getId(), session);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
		String receivedMessage = (String) message.getPayload();
		logger.info("Handling message: {} from sessionId {}", receivedMessage, session.getId());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		logger.info("Server connection with sessionId {} closed because of: {}", session.getId(), status.getReason());
		sessions.remove(session.getId());
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
		logger.error("error occured at sessionId {}", session, throwable);
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
