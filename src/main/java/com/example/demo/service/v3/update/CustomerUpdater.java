package com.example.demo.service.v3.update;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.support.EventBus;
import com.example.demo.service.support.v2.GenericUpdater;

@Service
@Transactional
public class CustomerUpdater extends GenericUpdater<Customer, Long, CustomerUpdatedDomainEvent> {

    public CustomerUpdater(CustomerRepository repository, EventBus eventBus) {
        super(repository, eventBus);
    }

    @Override
    protected CustomerUpdatedDomainEvent createDomainEvent(final Customer entity) {
        return new CustomerUpdatedDomainEvent(entity);
    }

}
