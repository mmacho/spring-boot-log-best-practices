package com.example.demo.service.support;

import java.io.Serializable;
import java.util.List;

public interface EventBus {

    void publish(final List<DomainEvent> events);
    
}
