package com.example.demo.service.v2;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.GenericQueryService;

@Service
public class CustomerQueryService extends GenericQueryService<Customer, Long> {

    public CustomerQueryService(CustomerRepository repository) {
        super(repository);
    }

}
