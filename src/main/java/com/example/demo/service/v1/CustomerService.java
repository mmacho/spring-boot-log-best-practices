package com.example.demo.service.v1;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.BaseEntity_;
import com.example.demo.domain.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.exception.ResourceNotFoundException;
import com.example.demo.service.exception.StaleStateIdentifiedException;

import io.github.perplexhub.rsql.RSQLJPASupport;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

	@NonNull
	private final CustomerRepository repository;

	// https://docs.oracle.com/javase/tutorial/java/generics/methods.html
	public Page<Customer> matching(Integer page, Integer size, String filter, String sort) {
		Specification<Customer> specification = RSQLJPASupport.<Customer>toSpecification(filter, Boolean.TRUE)
				.and(RSQLJPASupport.<Customer>toSort(sort));
		Pageable pageable = PageRequest.of(page, size);
		return repository.findAll(specification, pageable);
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
	public Customer findbyId(@NonNull final Long id) throws ResourceNotFoundException {
		return repository.findById(id).orElseThrow(() -> ResourceNotFoundException
				.forAggregateWith(MessageFormat.format("Customer not found for this id {0}", id)));
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
		Customer entity = findbyId(id);
		BeanUtils.copyProperties(customer, entity, BaseEntity_.ID, BaseEntity_.CREATED_AT, BaseEntity_.MODIFIED_AT);
		try {
			return repository.save(entity);
		} catch (OptimisticLockingFailureException e) {
			throw StaleStateIdentifiedException
					.forAggregateWith(MessageFormat.format("Confict to update entity with id {0}", id));
		}
	}

	/**
	 * 
	 * @param id
	 * @throws ResourceNotFoundException
	 */
	@Transactional
	public void delete(@NonNull final Long id) throws ResourceNotFoundException {
		Customer entity = findbyId(id);
		repository.delete(entity);
	}

}
