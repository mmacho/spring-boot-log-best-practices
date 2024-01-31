package com.example.demo.service.support.v2;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.BaseEntity;
import com.example.demo.repository.GenericRepository;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@Transactional
public abstract class GenericCreator<T extends BaseEntity<ID>, ID extends Serializable> {

    @NonNull
    private final GenericRepository<T, ID> repository;

    public T create(@NonNull T domain) {
        return this.repository.persist(domain);
    }
}
