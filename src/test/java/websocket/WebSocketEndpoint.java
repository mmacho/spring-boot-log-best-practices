package websocket;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

import lombok.extern.slf4j.Slf4j;

@MessageEndpoint
@Slf4j
public class WebSocketEndpoint {

	@ServiceActivator(inputChannel = "webSocketInboundChannel")
	public void process(String message) {
		log.info("Message : {}", message);
	}
}
