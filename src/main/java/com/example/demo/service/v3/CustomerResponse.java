package com.example.demo.service.v3;

import com.example.demo.service.support.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class CustomerResponse implements Response {

    private final String firstName;

	private final String lastName;

	private final String emailId;

}
