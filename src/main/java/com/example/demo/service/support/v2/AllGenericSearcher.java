package com.example.demo.service.support.v2;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.BaseEntity;
import com.example.demo.repository.GenericRepository;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@Transactional(readOnly = true)
public class AllGenericSearcher<T extends BaseEntity<ID>, ID extends Serializable> {

    @NonNull
    private final GenericRepository<T, ID> repository;

    public List<T> search() {
        Pageable pageable = PageRequest.of(0, 25);
        return this.repository.findAll(pageable).getContent();
    }
}
