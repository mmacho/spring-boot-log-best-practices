package com.example.demo.service.v2;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.GenericCommandService;

@Service
public class CustomerCommandService extends GenericCommandService<Customer, Long>{

    public CustomerCommandService(CustomerRepository repository) {
        super(repository);
    }

}
