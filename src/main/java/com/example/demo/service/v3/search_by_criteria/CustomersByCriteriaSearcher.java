package com.example.demo.service.v3.search_by_criteria;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.support.v2.GenericSearcherByCriteria;

@Service
@Transactional(readOnly = true)
public class CustomersByCriteriaSearcher extends GenericSearcherByCriteria<Customer, Long> {

    public CustomersByCriteriaSearcher(CustomerRepository repository) {
        super(repository);
    }

}
