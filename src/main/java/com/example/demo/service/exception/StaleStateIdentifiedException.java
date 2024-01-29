package com.example.demo.service.exception;

public class StaleStateIdentifiedException extends RuntimeException {
    
    private static final long serialVersionUID = 4382592954390171873L;
    
    private StaleStateIdentifiedException(String message) {
		super(message);
	}

	public static StaleStateIdentifiedException forAggregateWith(String messag) {
		return new StaleStateIdentifiedException(messag);
	}
}
