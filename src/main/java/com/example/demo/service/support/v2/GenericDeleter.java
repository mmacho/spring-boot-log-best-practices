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
@Transactional
public abstract class GenericDeleter<T extends BaseEntity, ID extends Serializable> {

    private final GenericRepository<T, ID> repository;

    public void delete(@NonNull final ID id) throws ResourceNotFoundException {
        final T entity = this.repository.findById(id).orElseThrow(() -> ResourceNotFoundException
                .forAggregateWith(MessageFormat.format("Entity not found for this id {0}", id)));
        this.repository.delete(entity);
    }
}
