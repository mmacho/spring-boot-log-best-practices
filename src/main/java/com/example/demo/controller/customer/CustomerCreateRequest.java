package com.example.demo.controller.customer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreateRequest {

	@JsonProperty(required = true)
	@NotEmpty
	@NotBlank
	private String firstName;

	@JsonProperty(required = true)
	@NotEmpty
	@NotBlank
	private String lastName;

	@JsonProperty(required = true)
	@NotEmpty
	@NotBlank
	private String emailId;

}
