package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.example.demo.domain.BaseEntity;

@NoRepositoryBean
public interface GenericRepository<T extends BaseEntity, ID extends Serializable > extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

}
