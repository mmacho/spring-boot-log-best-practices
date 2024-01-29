package com.example.demo.service.support.v3;

import java.io.Serializable;
import java.text.MessageFormat;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.BaseEntity;
import com.example.demo.repository.GenericRepository;
import com.example.demo.service.exception.ResourceNotFoundException;
import com.example.demo.service.support.Response;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@Transactional(readOnly = true)
public class GenericFinderV2<T extends BaseEntity<ID>, ID extends Serializable, R extends Response> {

    @NonNull
    private final GenericRepository<T, ID> repository;

    public R findbyId(@NonNull final ID id) throws ResourceNotFoundException {
        T entity = this.repository.findById(id).orElseThrow(() -> ResourceNotFoundException
                .forAggregateWith(MessageFormat.format("Entity not found for this id {0}", id)));
        R response = null;
        return response;
    }
}
