package com.example.demo.service.support.v2;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.BaseEntity;
import com.example.demo.repository.GenericRepository;
import com.example.demo.service.exception.ResourceNotFoundException;
import com.example.demo.service.exception.StaleStateIdentifiedException;
import com.example.demo.service.support.DomainEvent;
import com.example.demo.service.support.EventBus;

import lombok.NonNull;

@Transactional
public abstract class GenericUpdater<T extends BaseEntity<ID>, ID extends Serializable, E extends DomainEvent> {

    private final GenericRepository<T, ID> repository;

    private final EventBus eventBus;

    protected GenericUpdater(GenericRepository<T, ID> repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    public T update(@NonNull final ID id, @NonNull T domain) throws ResourceNotFoundException {
        try {
            T result = this.repository.save(domain);
            eventBus.publish(Arrays.asList(createDomainEvent(result)));
            return result;
        } catch (OptimisticLockingFailureException e) {
            throw StaleStateIdentifiedException
                    .forAggregateWith(MessageFormat.format("Confict to update entity with id {0}", id));
        }
    }

    protected abstract E createDomainEvent(T entity);

}
