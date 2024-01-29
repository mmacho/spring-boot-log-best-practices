package com.example.demo.service.support.v2;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.demo.controller.customer.BaseController;
import com.example.demo.domain.BaseEntity;
import com.example.demo.repository.GenericRepository;

import io.github.perplexhub.rsql.RSQLJPASupport;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@Transactional(readOnly = true)
public abstract class GenericSearcherByCriteria<T extends BaseEntity<ID>, ID extends Serializable> {

    private static final String DEFAULT_SORT = "id,desc";

    @NonNull
    private final GenericRepository<T, ID> repository;

    public Page<T> search(Integer page, Integer size, String filter, String sort) {
        pageChecker(page);
        sizeChecker(size);
        page = page > 0 ? page - 1 : 0;
        if (!StringUtils.hasText(sort)) {
            sort = DEFAULT_SORT;
        }
        final Specification<T> specification = RSQLJPASupport.<T>toSpecification(filter, Boolean.TRUE)
                .and(RSQLJPASupport.<T>toSort(sort));
        final Pageable pageable = PageRequest.of(page, size);
        return this.repository.findAll(specification, pageable);
    }
    private void pageChecker(Integer page) {
        if (page < 0) {
            //TODO: value from property, Custom exception, i18n
            throw new IllegalArgumentException("The page must not be less than zero");
        }
    }

    private void sizeChecker(Integer size) {
        if (size < 0) {
            throw new IllegalArgumentException("The page must not be less than zero");
        }
        if (size > 100) {
            throw new IllegalArgumentException("The size must not be greater than 100");
        }
    }
}
