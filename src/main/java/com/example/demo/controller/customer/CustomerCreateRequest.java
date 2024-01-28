package com.example.demo.controller.customer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.example.demo.controller.BaseRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomerCreateRequest extends BaseRequest {

	private static final long serialVersionUID = 8135554194506753620L;

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
