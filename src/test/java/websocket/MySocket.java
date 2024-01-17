package websocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// no funciona spring sleuth
@ServerEndpoint(value = "/sok")
@Component
public class MySocket {

	private static final Logger logger = LoggerFactory.getLogger(MySocket.class);

	private Map<String, Session> sessions = new ConcurrentHashMap<>();

	public MySocket() {
		super();
	}

	@OnOpen
	public void onOpen(Session session) throws Exception {
		logger.info("Server connection opened with sessionId {}", session.getId());
		sessions.put(session.getId(), session);
		sendMessage(session);

	}

	@OnMessage
	public void onMessage(Session session, String message) {
		logger.info("Handling message: {} from sessionId {}", message, session.getId());
	}

	@OnClose
	public void onClose(Session session) {
		logger.info("Server connection with sessionId {} closed", session.getId());
		sessions.remove(session.getId());

	}

	@OnError
	public void onError(Session session, Throwable exception) {
		logger.error("error occured at sessionId {}", session, exception);

	}

	private void sendMessage(Session session) throws Exception {
		if (session.isOpen()) {
			session.getBasicRemote().sendText("Hola");
			logger.info("Sended hello request message to sessionId {}", session.getId());
		}
	}

}
