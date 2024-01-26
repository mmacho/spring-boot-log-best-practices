package com.example.demo.service;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.BaseEntity_;
import com.example.demo.domain.Customer;
import com.example.demo.repository.CustomerRepository;
import lombok.NonNull;

@Service
@Transactional(readOnly = true)
public class CustomerService {

	private final CustomerRepository repository;

	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}

	public Page<Customer> search(int page, int size, String sortField, @NonNull String direction) {
		Sort sort = Sort.by(Sort.Direction.fromString(direction), sortField);
		Pageable pageable = PageRequest.of(page, size, sort);
		return repository.findAll(pageable);
	}

	public List<Customer> getAll() {
		return repository.findAll();
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public Customer getById(@NonNull final Long id) throws ResourceNotFoundException {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(getMessage(id)));
	}

	@Transactional
	public Customer create(@NonNull Customer customer) {
		return repository.save(customer);
	}

	/**
	 * 
	 * @param id
	 * @param customer
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Transactional
	public Customer update(@NonNull final Long id, @NonNull Customer customer) throws ResourceNotFoundException {
		Customer customerEntity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(getMessage(id)));
		BeanUtils.copyProperties(customer, customerEntity, BaseEntity_.ID, BaseEntity_.CREATED_AT, BaseEntity_.MODIFIED_AT);
		return repository.save(customerEntity);
	}

	/**
	 * 
	 * @param id
	 * @throws ResourceNotFoundException
	 */
	@Transactional
	public void delete(@NonNull final Long id) throws ResourceNotFoundException {
		Customer customer = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(getMessage(id)));
		repository.delete(customer);
	}

	private String getMessage(final Long id) {
		return MessageFormat.format("Customer not found for this id {0}", id);
	}
}
