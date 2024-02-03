package com.example.demo.service.v3.update;

import com.example.demo.domain.Customer;
import com.example.demo.service.support.DomainEvent;

public class CustomerUpdatedDomainEvent extends DomainEvent {

    private Customer entity;

    public CustomerUpdatedDomainEvent(Customer entity) {
        super(entity.getId());
        this.entity = entity;
    }

    @Override
    public String eventName() {
        return "customer.updated";
    }

    public Customer getEntity() {
        return entity;
    }
}
