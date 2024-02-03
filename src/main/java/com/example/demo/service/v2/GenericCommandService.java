package com.example.demo.service.v2;

import java.io.Serializable;
import java.text.MessageFormat;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.BaseEntity;
import com.example.demo.repository.GenericRepository;
import com.example.demo.service.exception.ResourceNotFoundException;
import com.example.demo.service.exception.StaleStateIdentifiedException;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Transactional
@AllArgsConstructor
public abstract class GenericCommandService<T extends BaseEntity<ID>, ID extends Serializable> {

    @NonNull
    private final GenericRepository<T, ID> repository;

    public T create(@NonNull T domain) {
        return this.repository.save(domain);
    }

    public T update(@NonNull final ID id, @NonNull T domain) throws ResourceNotFoundException {
        domain.setId(id);
        try {
            return this.repository.save(domain);
            //TODO: add more exceptions
        } catch (OptimisticLockingFailureException e) {
            throw StaleStateIdentifiedException
                    .forAggregateWith(MessageFormat.format("Confict to update entity with id {0}", id));
        }
    }
    
    public void delete(@NonNull final ID id) throws ResourceNotFoundException {
        this.repository.deleteById(id);
    }

}
