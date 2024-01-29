package com.example.demo.service.v3;

import java.util.List;

import com.example.demo.service.support.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class CustomersResponse implements Response {

    private final List<CustomerResponse> customers;

}
