package com.example.demo.service.v2;

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
public abstract class GenericCommandService<T extends BaseEntity<ID>, ID extends Serializable> {

    @NonNull
    private final GenericRepository<T, ID> repository;

    @Transactional
    public T create(@NonNull T domain) {
        return this.repository.save(domain);
    }

    @Transactional
    public T update(@NonNull final ID id, @NonNull T domain) throws ResourceNotFoundException {
		final T entity = findbyId(id);
        BeanUtils.copyProperties(domain, entity, BaseEntity_.ID, BaseEntity_.CREATED_AT, BaseEntity_.MODIFIED_AT);
        try {
            return this.repository.save(entity);
            //TODO: add more exceptions
        } catch (OptimisticLockingFailureException e) {
            throw StaleStateIdentifiedException
                    .forAggregateWith(MessageFormat.format("Confict to update entity with id {0}", id));
        }
    }

    @Transactional
    public void delete(@NonNull final ID id) throws ResourceNotFoundException {
		final T entity = findbyId(id);
        this.repository.delete(entity);
    }

    public T findbyId(@NonNull final ID id) throws ResourceNotFoundException {
        return this.repository.findById(id).orElseThrow(() -> ResourceNotFoundException
                .forAggregateWith(MessageFormat.format("Entity not found for this id {0}", id)));
    }
}
