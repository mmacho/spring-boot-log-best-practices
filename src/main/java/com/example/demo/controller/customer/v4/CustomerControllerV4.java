package com.example.demo.controller.customer.v4;

import java.net.URI;
import java.time.Duration;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.CacheControl;
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
import org.springframework.web.context.request.WebRequest;
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
@RequestMapping(value = "/v4/customer")
@Validated
public class CustomerControllerV4 extends BaseController {


    @NonNull
    private final CustomerService service;

    @NonNull
    private final CustomerMapper customerMapper;

    @GetMapping
    @Timed
    public ResponseEntity<Response<List<CustomerResponse>>> findAll() {
        final List<Customer> entities = service.getAll();
        return entities.isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok().body(customerMapper.toGenericResponse(entities));
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
                : ResponseEntity.ok().body(customerMapper.toGenericResponse(entities));
    }

    @GetMapping(value = ID, params = FIELDS)
    @Timed
    public ResponseEntity<Response<CustomerResponse>> findById(@PathVariable final Long id, WebRequest request,
            @RequestParam(required = false, value = FIELDS) String... fields) {
        final Customer entity = service.findbyId(id);
        final String etag = String.valueOf(entity.getVersion());
        if (request.checkNotModified(etag)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).cacheControl(CacheControl.maxAge(Duration.ofDays(1)))
                    .build();
        }
        final CustomerResponse response = customerMapper.toResponse(entity);
        final MappingJacksonValue filterResponse = filter(response, CustomerResponse.FIELDS_FILTER,
                fields);
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(Duration.ofDays(1))).eTag(etag)
                .body(Response.success((CustomerResponse) filterResponse.getValue()));
    }

    @PostMapping
    @Timed
    public ResponseEntity<Response<CustomerResponse>> create(@Valid @RequestBody final CustomerCreateRequest request) {
        final Customer entity = service.create(customerMapper.toDomain(request));
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(ID)
                .buildAndExpand(entity.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(location)
                .body(customerMapper.toGenericResponse(entity));

    }

    @PutMapping(value = ID)
    @Timed
    public ResponseEntity<Response<CustomerResponse>> update(@PathVariable final Long id,
            @Valid @RequestBody final CustomerUpdateRequest request) {
        final Customer entity = service.update(id, customerMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.OK).body(customerMapper.toGenericResponse(entity));
    }

    @DeleteMapping(value = ID)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
