package com.example.demo.service.support;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.demo.service.v3.update.CustomerUpdatedDomainEvent;

@Component
public class SpringApplicationEventListener {

    @EventListener
    public void handleSuccessful(CustomerUpdatedDomainEvent event) {
        System.out.println("CustomerUpdatedDomainEvent received with id: " + event.getEntity().getId());
    }

}
