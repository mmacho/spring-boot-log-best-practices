package com.example.demo.controller;

import java.io.Serializable;
import java.time.Instant;

import javax.validation.constraints.NotNull;

import com.example.demo.controller.support.Responses;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseResponse implements Serializable, Responses {

	public static final String FIELDS_FILTER = "FIELDS_FILTER";

	private static final long serialVersionUID = 7434883984803641757L;

	@JsonProperty(required = true)
	@NotNull
	private Long id;

	@JsonProperty(required = true)
	@NotNull
	private Long version;

	@JsonProperty(required = true)
	@NotNull
	private Instant createdAt;

	private Instant modifiedAt;

}
