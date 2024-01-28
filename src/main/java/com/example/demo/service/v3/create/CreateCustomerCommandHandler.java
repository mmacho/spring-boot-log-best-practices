package com.example.demo.service.v3.create;

import org.springframework.stereotype.Service;

import com.example.demo.service.support.CommandHandler;
import com.example.demo.service.v3.CustomerResponse;

@Service
public class CreateCustomerCommandHandler implements CommandHandler<CreateCustomerCommand, CustomerResponse>{

    @Override
    public CustomerResponse handle(CreateCustomerCommand command) {
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }

}
