package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.example.demo.domain.BaseEntity;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;

@NoRepositoryBean
public interface GenericRepository<T extends BaseEntity<ID>, ID extends Serializable> extends BaseJpaRepository<T, ID>, JpaSpecificationExecutor<T>, PagingAndSortingRepository<T, ID>{

}
