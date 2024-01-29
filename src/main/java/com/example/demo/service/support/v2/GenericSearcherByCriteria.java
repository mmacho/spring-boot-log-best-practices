package com.example.demo.service.support.v2;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.BaseEntity;
import com.example.demo.repository.GenericRepository;

import io.github.perplexhub.rsql.RSQLJPASupport;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Transactional(readOnly = true)
public abstract class GenericSearcherByCriteria<T extends BaseEntity, ID extends Serializable> {

    private final GenericRepository<T, ID> repository;

    public Page<T> search(Integer page, Integer size, String filter, String sort) {
        final Specification<T> specification = RSQLJPASupport.<T>toSpecification(filter, Boolean.TRUE)
                .and(RSQLJPASupport.<T>toSort(sort));
        final Pageable pageable = PageRequest.of(page, size);
        return this.repository.findAll(specification, pageable);
    }
}
