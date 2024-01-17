package controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integration")
public class IntegrationController {

	private IntegrationGateway integrationGateway;

	public IntegrationController(IntegrationGateway integrationGateway) {
		this.integrationGateway = integrationGateway;
	}

	@GetMapping(value = "/{name}")
	public String send(@PathVariable("name") String name) {
		return integrationGateway.sendMessage(name);
	}
}
