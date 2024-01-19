package com.example.demo.controller;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class DemoExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public void internalServerError(Exception e) {
		MDC.put("rootCause", getRootCause(e).getClass().getName());
		log.error("returning 500 (internal server error).", e);
		MDC.remove("rootCause");
	}

	private Throwable getRootCause(Exception e) {
		Throwable rootCause = e;
		if (e.getCause() != null && rootCause.getCause() != rootCause) {
			rootCause = e.getCause();
		}
		return rootCause;
	}

}
