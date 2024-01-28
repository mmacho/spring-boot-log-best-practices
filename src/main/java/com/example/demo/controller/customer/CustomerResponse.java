package com.example.demo.controller.customer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.example.demo.controller.BaseResponse;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

//@JsonFilter(BaseResponse.FIELDS_FILTER)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = {})
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse extends BaseResponse {

	private static final long serialVersionUID = 3754724069120630651L;

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
