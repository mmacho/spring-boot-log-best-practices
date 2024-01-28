package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.Customer;

@Repository
public interface CustomerRepository extends GenericRepository<Customer, Long> {

}
