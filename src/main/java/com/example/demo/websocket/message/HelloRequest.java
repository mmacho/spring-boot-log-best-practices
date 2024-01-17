package com.example.demo.websocket.message;

import java.io.Serializable;

public class HelloRequest implements Serializable {

	private static final long serialVersionUID = 4988154835715258818L;

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
