package com.example.demo.controller.customer.v1;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.controller.Response;
import com.example.demo.controller.customer.BaseController;
import com.example.demo.controller.customer.CustomerCreateRequest;
import com.example.demo.controller.customer.CustomerMapper;
import com.example.demo.controller.customer.CustomerResponse;
import com.example.demo.controller.customer.CustomerUpdateRequest;
import com.example.demo.domain.Customer;
import com.example.demo.service.v1.CustomerService;

import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/customer")
@Validated
public class CustomerControllerV1 extends BaseController {

	@NonNull
	private final CustomerService service;

	@GetMapping
	@Timed
	public ResponseEntity<Response<List<CustomerResponse>>> findAll() {
		final List<Customer> entities = service.getAll();
		return entities.isEmpty() ? ResponseEntity.noContent().build()
				: ResponseEntity.ok().body(CustomerMapper.INSTANCE.toGenericResponse(entities));
	}

	@GetMapping(value = SEARCH)
	@Timed
	public ResponseEntity<Response<Page<CustomerResponse>>> searchBy(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "25") Integer size,
			@RequestParam(required = false, name = "sortField", defaultValue = "id") String sortField,
			@RequestParam(required = false, name = "direction", defaultValue = "DESC") String direction) {
		final Page<Customer> entities = service.search(page, size, sortField, direction);
		return entities.isEmpty() ? ResponseEntity.noContent().build()
				: ResponseEntity.ok().body(CustomerMapper.INSTANCE.toGenericResponse(entities));
	}

	@GetMapping(value = MACHING)
	@Timed
	public ResponseEntity<Response<Page<CustomerResponse>>> matching(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "25") Integer size,
			@RequestParam(required = false, name = "filter") String filter,
			@RequestParam(required = false, name = "sort", defaultValue = "id,desc") String sort) {
		final Page<Customer> entities = service.matching(page, size, filter, sort);
		return entities.isEmpty() ? ResponseEntity.noContent().build()
				: ResponseEntity.ok().body(CustomerMapper.INSTANCE.toGenericResponse(entities));
	}

	@GetMapping(value = ID)
	@Timed
	public ResponseEntity<Response<CustomerResponse>> findById(@PathVariable final Long id) {
		return ResponseEntity.ok().body(CustomerMapper.INSTANCE.toGenericResponse(service.findbyId(id)));
	}

	@GetMapping(value = ID, params = "fields")
	@Timed
	public ResponseEntity<Response<MappingJacksonValue>> findById2(@PathVariable final Long id, @RequestParam(required = false, value = "fields") String... fields) {
		Response<CustomerResponse> response = CustomerMapper.INSTANCE.toGenericResponse(service.findbyId(id));
		return ResponseEntity.ok().body(filterResponse(response, CustomerResponse.FIELDS_FILTER, fields));
	}

	@PostMapping
	@Timed
	public ResponseEntity<Response<CustomerResponse>> create(@Valid @RequestBody final CustomerCreateRequest request) {
		final Customer entity = service.create(CustomerMapper.INSTANCE.toDomain(request));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(entity.getId())
				.toUri();
		return ResponseEntity.status(HttpStatus.CREATED).location(location)
				.body(CustomerMapper.INSTANCE.toGenericResponse(entity));

	}

	@PutMapping(value = ID)
	@Timed
	public ResponseEntity<Response<CustomerResponse>> update(@PathVariable final Long id,
			@Valid @RequestBody final CustomerUpdateRequest request) {
		final Customer entity = service.update(id, CustomerMapper.INSTANCE.toDomain(request));
		return ResponseEntity.status(HttpStatus.OK).body(CustomerMapper.INSTANCE.toGenericResponse(entity));
	}

	@DeleteMapping(value = ID)
	@Timed
	public ResponseEntity<Void> delete(@PathVariable final Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
