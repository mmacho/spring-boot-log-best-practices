package com.example.demo.service.v3;

import org.springframework.data.domain.Page;

import com.example.demo.service.support.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/*
 * Page es una concesión rápida igual debería existir un page propio y no de spring
 */
@Getter
@Builder
@AllArgsConstructor
@ToString
public class CustomersPageReponse implements Response {

    private final Page<CustomerResponse> customers;
}
