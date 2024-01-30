package com.example.demo.controller.customer.v5;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.customer.CustomerCreateRequest;
import com.example.demo.controller.customer.CustomerResponse;
import com.example.demo.controller.customer.CustomerUpdateRequest;
import com.example.demo.controller.customer.v5.mapper.CustomerCreateRequestMapper;
import com.example.demo.controller.customer.v5.mapper.CustomerReponseMapper;
import com.example.demo.controller.customer.v5.mapper.CustomerUpdateRequestMapper;
import com.example.demo.controller.support.v1.GenericCommandController;
import com.example.demo.domain.Customer;
import com.example.demo.service.v2.CustomerCommandService;

/*
 * code smell con varios objetos request
 */
@RestController
@RequestMapping(value = "/v5/customer")
public class CustomerCommandController extends GenericCommandController<Customer, Long, CustomerCreateRequest, CustomerUpdateRequest, CustomerResponse> {

    public CustomerCommandController(CustomerCommandService service, CustomerCreateRequestMapper reqCreateMapper, CustomerUpdateRequestMapper reqUpdateMapper, CustomerReponseMapper resMapper) {
        super(service, reqCreateMapper, reqUpdateMapper, resMapper);
    }   
}
