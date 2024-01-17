package configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

//no funciona spring sleuth
@Configuration
@ComponentScan(basePackages = "com.example.demo.websocket")
public class WebSocketConfiguration2 {

	@Bean
	ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
}
