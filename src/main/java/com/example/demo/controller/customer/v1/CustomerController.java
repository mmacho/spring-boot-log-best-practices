package com.example.demo.controller.customer.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.customer.BaseController;
import com.example.demo.controller.customer.CustomerCreateRequest;
import com.example.demo.controller.customer.CustomerMapper;
import com.example.demo.controller.customer.CustomerResponse;
import com.example.demo.controller.customer.CustomerUpdateRequest;
import com.example.demo.domain.Customer;
import com.example.demo.service.CustomerService;

import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("/api/v1/customer")
@Validated
public class CustomerController extends BaseController {

	private final CustomerService service;

	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@GetMapping
	@Timed
	public ResponseEntity<List<CustomerResponse>> findAll() {
		final List<Customer> entities = service.getAll();
		return entities.isEmpty() ? ResponseEntity.noContent().build()
				: ResponseEntity.ok().body(CustomerMapper.INSTANCE.toListResponse(entities));
	}

	@GetMapping(value = SEARCH)
	@Timed
	public ResponseEntity<Page<CustomerResponse>> searchBy(@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "10") Integer size,
			@RequestParam(required = false, name = "sortField", defaultValue = "id") String sortField,
			@RequestParam(required = false, name = "direction", defaultValue = "DESC") String direction) {
		final Page<Customer> entities = service.search(page, size, sortField, direction);
		return entities.isEmpty() ? ResponseEntity.noContent().build()
				: ResponseEntity.ok().body(CustomerMapper.INSTANCE.toPageResponse(entities));
	}

	@GetMapping(value = ID)
	@Timed
	public ResponseEntity<CustomerResponse> findById(@PathVariable final Long id) {
		CustomerResponse entity = CustomerMapper.INSTANCE.toResponse(service.getById(id));
		return ResponseEntity.ok().body(entity);
	}

	@PostMapping
	@Timed
	public ResponseEntity<CustomerResponse> create(@Valid @RequestBody final CustomerCreateRequest request) {
		Customer entity = service.create(CustomerMapper.INSTANCE.toDomain(request));
		return ResponseEntity.status(HttpStatus.CREATED).body(CustomerMapper.INSTANCE.toResponse(entity));

	}

	@PutMapping(value = ID)
	@Timed
	public ResponseEntity<CustomerResponse> update(@PathVariable final Long id,
			@Valid @RequestBody final CustomerUpdateRequest request) {
		Customer entity = service.update(id, CustomerMapper.INSTANCE.toDomain(request));
		return ResponseEntity.status(HttpStatus.OK).body(CustomerMapper.INSTANCE.toResponse(entity));
	}

	@DeleteMapping(value = ID)
	@Timed
	public ResponseEntity<Void> delete(@PathVariable final Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
