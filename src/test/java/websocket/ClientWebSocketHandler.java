package websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.demo.websocket.message.HelloRequest;
import com.example.demo.websocket.message.HelloResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ClientWebSocketHandler extends TextWebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger(ClientWebSocketHandler.class);

	private final ObjectMapper objectMapper;

	public ClientWebSocketHandler(final ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("Client connection opened {}", session.getId());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		logger.info("Client connection closed {}", session.getId());
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		logger.info("Client {} received: {}", session.getId(), message);
		HelloRequest request = objectMapper.readValue(message.getPayload(), HelloRequest.class);
		logger.info("Client {} received: {}", session.getId(), request);
		sendMessage(session);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) {
		logger.info("Client transport error: {}", exception.getMessage());
	}

	private void sendMessage(WebSocketSession session) throws Exception {
		if (session.isOpen()) {
			HelloResponse r = new HelloResponse();
			r.setMessage("The response");
			TextMessage message = new TextMessage(objectMapper.writeValueAsString(r));
			session.sendMessage(message);
		}
	}
}
