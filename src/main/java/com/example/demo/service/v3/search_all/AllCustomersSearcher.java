package com.example.demo.service.v3.search_all;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.support.v2.AllGenericSearcher;

@Service
@Transactional(readOnly = true)
public class AllCustomersSearcher extends AllGenericSearcher<Customer, Long >{

    public AllCustomersSearcher(CustomerRepository repository) {
        super(repository);
    }

}
