package com.example.demo.service.v3.delete;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.support.v2.GenericDeleter;

@Service
@Transactional
public class CustomerDeleter extends GenericDeleter<Customer, Long>{

    public CustomerDeleter(CustomerRepository repository) {
        super(repository);
    }

}
