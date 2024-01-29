package com.example.demo.service.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4382592954390171873L;

	private ResourceNotFoundException(String message) {
		super(message);
	}

	public static ResourceNotFoundException forAggregateWith(String messag) {
		return new ResourceNotFoundException(messag);
	}
}
