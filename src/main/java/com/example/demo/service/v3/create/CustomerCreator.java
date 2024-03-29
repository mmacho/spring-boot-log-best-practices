package com.example.demo.service.v3.create;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.support.v2.GenericCreator;

@Service
@Transactional
public class CustomerCreator extends GenericCreator<Customer, Long> {

    public CustomerCreator(CustomerRepository repository) {
        super(repository);
    }

}
