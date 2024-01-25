package com.example.demo.service;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4382592954390171873L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
