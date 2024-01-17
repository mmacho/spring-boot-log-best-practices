package websocket;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@ClientEndpoint
@Component
@DependsOn({ "mySocket" })
public class MySocket2 {

	private static final Logger logger = LoggerFactory.getLogger(MySocket2.class);

	private final String uri = "ws://localhost:8081/sok";

	public MySocket2() {
		super();
	}

	@EventListener({ ContextRefreshedEvent.class })
	public void init() {
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		try {
			container.connectToServer(this, new URI(uri));
		} catch (DeploymentException | IOException | URISyntaxException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@OnOpen
	public void onOpen(Session session) {
		logger.info("Server connection opened with sessionId {}", session.getId());

	}

	@OnMessage
	public void onMessage(String message, Session session) {
		logger.info("Handling message: {} from sessionId {}", message, session.getId());

	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info("Server connection with sessionId {} closed", session.getId());

	}
}
