package com.example.demo.controller.customer.v5.mapper;

import org.mapstruct.Mapper;

import com.example.demo.controller.ResponseMapper;
import com.example.demo.controller.customer.CustomerResponseFilter;
import com.example.demo.domain.Customer;

@Mapper(componentModel = "spring")
public interface CustomerReponseFilterMapper extends ResponseMapper<CustomerResponseFilter, Customer> {

}
