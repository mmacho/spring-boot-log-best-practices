package com.example.demo.service.v3.delete;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Customer;
import com.example.demo.service.v3.CustomerResponse;

@Service
public class DeleteCustomerCommandHandlerV2 extends DeleteGenericCommandHandler<Customer, Long, CustomerResponse> {

    public DeleteCustomerCommandHandlerV2(CustomerDeleter deleter) {
        super(deleter);
    }

}
