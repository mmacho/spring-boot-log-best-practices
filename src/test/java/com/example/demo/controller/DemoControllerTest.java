package com.example.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.service.DemoService;

@ExtendWith(MockitoExtension.class)
class DemoControllerTest {

	@Mock
	private DemoService demoService;

	@InjectMocks
	private DemoController demoController;

	@BeforeEach
	void setMockOutput() {
		when(demoService.getWelcomeMessage()).thenReturn("Hello Mockito Test");
	}

	@Test
	void shouldReturnDefaultMessage() {
		String response = demoController.greeting();
		assertThat(response).isEqualTo("Hello Mockito Test");
	}
}
