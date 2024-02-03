package com.example.demo.service.support;

import java.io.Serializable;
import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

public abstract class DomainEvent {

    private Long aggregateId;

    private String eventId;
    
    private Instant occurredOn;

    protected DomainEvent(Long aggregateId) {
        this.aggregateId = aggregateId;
        this.eventId = UUID.randomUUID().toString();
        this.occurredOn = Instant.now(Clock.systemUTC());
    }

    protected DomainEvent() {
    }

    public abstract String eventName();

    public Long getAggregateId() {
        return aggregateId;
    }

    public String getEventId() {
        return eventId;
    }

    public Instant getOccurredOn() {
        return occurredOn;
    }


}
