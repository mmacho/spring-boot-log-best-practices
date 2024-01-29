package com.example.demo.service.v3.find;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.support.v2.GenericFinder;

@Service
@Transactional(readOnly = true)
public class CustomerFinder extends GenericFinder<Customer, Long> {

    public CustomerFinder(CustomerRepository repository) {
        super(repository);
    }

}
