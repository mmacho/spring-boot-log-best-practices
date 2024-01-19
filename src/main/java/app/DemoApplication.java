package app;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import com.example.demo.configuration.DemoConfiguration;

@SpringBootApplication
@Import({ DemoConfiguration.class })
public class DemoApplication {

	private final RestTemplate restTemplate;

	public DemoApplication(RestTemplate restTemplate) {

		this.restTemplate = restTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	private void callRest() {
		var start = System.nanoTime();
		try {
			restTemplate.getForEntity("http://localhost:8123", String.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			var end = System.nanoTime();
			System.out.println("Request duration in Ms: " + ((end - start) / 1_000_000));
		}
	}

	// @Bean
	CommandLineRunner commandLineRunner() {
		return args -> {
			try {
				var executorService = Executors.newFixedThreadPool(100);
				for (int i = 0; i < 1; i++) {
					executorService.submit(this::callRest);
				}
				executorService.shutdown();
				executorService.awaitTermination(60, TimeUnit.SECONDS);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		};
	}
}
