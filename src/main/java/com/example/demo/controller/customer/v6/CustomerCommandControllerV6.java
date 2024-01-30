package com.example.demo.controller.customer.v6;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.customer.CustomerCreateRequest;
import com.example.demo.controller.customer.CustomerResponseFilter;
import com.example.demo.controller.customer.CustomerUpdateRequest;
import com.example.demo.controller.customer.v5.mapper.CustomerCreateRequestMapper;
import com.example.demo.controller.customer.v5.mapper.CustomerReponseFilterMapper;
import com.example.demo.controller.customer.v5.mapper.CustomerUpdateRequestMapper;
import com.example.demo.controller.support.v2.GenericCommandControllerV2;
import com.example.demo.domain.Customer;
import com.example.demo.service.v2.CustomerCommandService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/*
 * code smell con varios objetos request
 * CustomerResponseFilter aqui no tiene sentido en el command debería ser CustomerResponse
 */
@RestController
@RequestMapping(value = "/v6/customer")
@Tag(name = "Customer v6", description = "The Customer API")
public class CustomerCommandControllerV6 extends
        GenericCommandControllerV2<Customer, Long, CustomerCreateRequest, CustomerUpdateRequest, CustomerResponseFilter> {

    public CustomerCommandControllerV6(CustomerCommandService service, CustomerCreateRequestMapper reqCreateMapper,
            CustomerUpdateRequestMapper reqUpdateMapper, CustomerReponseFilterMapper resMapper) {
        super(service, reqCreateMapper, reqUpdateMapper, resMapper);
    }

    @Override
    @Operation(summary = "Create Customer")
    public ResponseEntity<MappingJacksonValue> create(@Valid CustomerCreateRequest request) {
        return super.create(request);
    }

    @Override
    @Operation(summary = "Delete Customer")
    public ResponseEntity<Void> delete(Long id) {
        return super.delete(id);
    }

    @Override
    @Operation(summary = "Update Customer")
    public ResponseEntity<MappingJacksonValue> update(Long id, @Valid CustomerUpdateRequest request) {
        return super.update(id, request);
    }

}
