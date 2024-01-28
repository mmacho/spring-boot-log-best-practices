package com.example.demo.controller.customer.v5;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.GenericQueryController;
import com.example.demo.controller.customer.CustomerResponse;
import com.example.demo.controller.customer.v5.mapper.CustomerReponseMapper;
import com.example.demo.domain.Customer;
import com.example.demo.service.v2.CustomerQueryService;

@RestController
@RequestMapping(value = "/v5/customer")
public class CustomerQueryController extends GenericQueryController<Customer, Long, CustomerResponse> {

    public CustomerQueryController(final CustomerQueryService service, final CustomerReponseMapper mapper) {
        super(service, mapper);
    }

}
