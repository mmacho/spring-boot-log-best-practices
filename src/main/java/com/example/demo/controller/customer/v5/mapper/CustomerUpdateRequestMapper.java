package com.example.demo.controller.customer.v5.mapper;

import org.mapstruct.Mapper;

import com.example.demo.controller.RequestMapper;
import com.example.demo.controller.customer.CustomerUpdateRequest;
import com.example.demo.domain.Customer;

@Mapper(componentModel = "spring")
public interface CustomerUpdateRequestMapper extends RequestMapper<CustomerUpdateRequest, Customer> {

}
