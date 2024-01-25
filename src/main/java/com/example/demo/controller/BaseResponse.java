package com.example.demo.controller;

import java.io.Serializable;
import java.time.Instant;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseResponse implements Serializable {

	private static final long serialVersionUID = 6889974969225304744L;

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
