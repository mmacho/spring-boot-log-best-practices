package com.example.demo.service;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.BaseEntity;
import com.example.demo.repository.GenericRepository;

import io.github.perplexhub.rsql.RSQLJPASupport;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@Transactional(readOnly = true)
public class GenericQueryService<T extends BaseEntity, ID extends Serializable> {

    private final GenericRepository<T, ID> repository;

    public Page<T> matching(Integer page, Integer size, String filter, String sort) {
        final Specification<T> specification = RSQLJPASupport.<T>toSpecification(filter, Boolean.TRUE)
                .and(RSQLJPASupport.<T>toSort(sort));
        final Pageable pageable = PageRequest.of(page, size);
        return this.repository.findAll(specification, pageable);
    }

    public List<T> getAll() {
        return this.repository.findAll();
    }

    public T findbyId(@NonNull final ID id) throws ResourceNotFoundException {
        return this.repository.findById(id).orElseThrow(() -> ResourceNotFoundException
                .forAggregateWith(MessageFormat.format("Entity not found for this id {0}", id)));
    }

}
