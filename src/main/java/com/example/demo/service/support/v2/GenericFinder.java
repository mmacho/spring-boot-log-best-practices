package com.example.demo.service.support.v2;

import java.io.Serializable;
import java.text.MessageFormat;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.BaseEntity;
import com.example.demo.repository.GenericRepository;
import com.example.demo.service.exception.ResourceNotFoundException;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@Transactional(readOnly = true)
public abstract class GenericFinder<T extends BaseEntity, ID extends Serializable> {

    private final GenericRepository<T, ID> repository;

    public T findbyId(@NonNull final ID id) throws ResourceNotFoundException {
        return this.repository.findById(id).orElseThrow(() -> ResourceNotFoundException
                .forAggregateWith(MessageFormat.format("Entity not found for this id {0}", id)));
    }
}
