package configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

// https://docs.spring.io/spring-framework/reference/web/websocket/server.html
// no funciona spring sleuth
@Configuration
@EnableWebSocket
@ComponentScan(basePackages = "com.example.demo.websocket")
public class WebSocketConfiguration implements WebSocketConfigurer {

	WebSocketHandler serverWebSocketHandler;

	public WebSocketConfiguration(WebSocketHandler serverWebSocketHandler) {
		this.serverWebSocketHandler = serverWebSocketHandler;
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(serverWebSocketHandler, "/websocket").setAllowedOrigins("*")
				.setHandshakeHandler(handshakeHandler()).addInterceptors(new HttpSessionHandshakeInterceptor());
	}

	@Bean
	DefaultHandshakeHandler handshakeHandler() {
		return new DefaultHandshakeHandler(new TomcatRequestUpgradeStrategy());

	}

	@Configuration
	public class ClientWebSocketConfig {

		private static final String URI = "ws://localhost:8081/websocket";

		WebSocketHandler clientWebSocketHandler;

		public ClientWebSocketConfig(WebSocketHandler clientWebSocketHandler) {
			this.clientWebSocketHandler = clientWebSocketHandler;
		}

		@Bean
		WebSocketConnectionManager webSocketConnectionManager() {
			WebSocketConnectionManager manager = new WebSocketConnectionManager(webSocketClient(),
					clientWebSocketHandler, URI);
			manager.setAutoStartup(Boolean.TRUE);
			return manager;
		}

		@Bean
		WebSocketClient webSocketClient() {
			return new StandardWebSocketClient();
		}

	}

}
