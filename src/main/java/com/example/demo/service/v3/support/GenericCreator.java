package com.example.demo.service.v3.support;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.BaseEntity;
import com.example.demo.repository.GenericRepository;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public abstract class GenericCreator<T extends BaseEntity, ID extends Serializable> {

    private final GenericRepository<T, ID> repository;

    @Transactional
    public T create(@NonNull T domain) {
        return this.repository.save(domain);
    }
}
