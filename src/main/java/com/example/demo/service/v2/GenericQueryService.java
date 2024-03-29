package com.example.demo.service.v2;

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
import com.example.demo.service.exception.ResourceNotFoundException;

import io.github.perplexhub.rsql.RSQLJPASupport;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@Transactional(readOnly = true)
public abstract class GenericQueryService<T extends BaseEntity<ID>, ID extends Serializable> {

    private final GenericRepository<T, ID> repository;

    public Page<T> matching(Integer page, Integer size, String filter, String sort) {
        final Specification<T> specification = RSQLJPASupport.<T>toSpecification(filter, Boolean.TRUE)
                .and(RSQLJPASupport.<T>toSort(sort));
        final Pageable pageable = PageRequest.of(page, size);
        return this.repository.findAll(specification, pageable);
    }

    public Page<T> all(final Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    public List<T> findAll() {
        return this.repository.findAll();
    }

    public T findbyId(@NonNull final ID id) throws ResourceNotFoundException {
        return this.repository.findById(id).orElseThrow(() -> ResourceNotFoundException
                .forAggregateWith(MessageFormat.format("Entity not found for this id {0}", id)));
    }

}
