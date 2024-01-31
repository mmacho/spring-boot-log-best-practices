package com.example.demo.service.support.v3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.BaseEntity;
import com.example.demo.repository.GenericRepository;
import com.example.demo.service.support.Response;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@Transactional(readOnly = true)
public class AllGenericSearcherV2<T extends BaseEntity<ID>, ID extends Serializable, R extends Response> {

    @NonNull
    private final GenericRepository<T, ID> repository;

    public List<R> search() {
        final Pageable pageable = PageRequest.of(0, 25);
        final List<T> entities = this.repository.findAll(pageable).getContent();
        return new ArrayList<>();
    }

}
