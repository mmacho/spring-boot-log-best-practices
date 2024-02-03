package com.example.demo.controller.customer;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateRequest extends CustomerCreateRequest {

	private static final long serialVersionUID = -2535882365604844348L;
	
	@JsonProperty(required = true)
	@NotNull
	private Long version;

}
