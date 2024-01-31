package com.example.demo.service.support.v2;

import java.io.Serializable;
import java.text.MessageFormat;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.BaseEntity;
import com.example.demo.domain.BaseEntity_;
import com.example.demo.repository.GenericRepository;
import com.example.demo.service.exception.ResourceNotFoundException;
import com.example.demo.service.exception.StaleStateIdentifiedException;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@Transactional
public abstract class GenericUpdater<T extends BaseEntity<ID>, ID extends Serializable> {

    private final GenericRepository<T, ID> repository;

    public T update(@NonNull final ID id, @NonNull T domain) throws ResourceNotFoundException {
        final T entity = this.repository.findById(id).orElseThrow(() -> ResourceNotFoundException
                .forAggregateWith(MessageFormat.format("Entity not found for this id {0}", id)));
        BeanUtils.copyProperties(domain, entity, BaseEntity_.ID, BaseEntity_.CREATED_AT, BaseEntity_.MODIFIED_AT);
        try {
            return this.repository.persist(entity);
            // TODO: add more exceptions
        } catch (OptimisticLockingFailureException e) {
            throw StaleStateIdentifiedException
                    .forAggregateWith(MessageFormat.format("Confict to update entity with id {0}", id));
        }
    }
}
