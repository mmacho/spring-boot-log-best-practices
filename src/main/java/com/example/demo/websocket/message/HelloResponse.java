package com.example.demo.websocket.message;

import java.io.Serializable;

public class HelloResponse implements Serializable {

	private static final long serialVersionUID = 8838447903312251294L;

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
