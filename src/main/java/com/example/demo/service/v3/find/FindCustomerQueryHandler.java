package com.example.demo.service.v3.find;

import org.springframework.stereotype.Service;

import com.example.demo.service.support.QueryHandler;
import com.example.demo.service.v3.CustomerResponse;

@Service
public class FindCustomerQueryHandler implements QueryHandler<FindCustomerQuery, CustomerResponse> {

    @Override
    public CustomerResponse handle(FindCustomerQuery query) {
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }

}
