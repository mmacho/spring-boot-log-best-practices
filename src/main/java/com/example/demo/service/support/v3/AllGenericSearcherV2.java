package com.example.demo.service.support.v3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.BaseEntity;
import com.example.demo.repository.GenericRepository;
import com.example.demo.service.support.Response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Transactional(readOnly = true)
public class AllGenericSearcherV2<T extends BaseEntity, ID extends Serializable, R extends Response> {

    private final GenericRepository<T, ID> repository;

    public List<R> search() {
        final List<T> entities = this.repository.findAll();
        return new ArrayList<>();
    }

}
