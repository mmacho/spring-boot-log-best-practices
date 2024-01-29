package com.example.demo.service.support.v2;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.BaseEntity;
import com.example.demo.repository.GenericRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Transactional(readOnly = true)
public class AllGenericSearcher<T extends BaseEntity, ID extends Serializable> {

    private final GenericRepository<T, ID> repository;

    public List<T> search() {
        return this.repository.findAll();
    }
}
