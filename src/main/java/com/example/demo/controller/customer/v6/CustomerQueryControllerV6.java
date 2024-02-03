package com.example.demo.controller.customer.v6;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.example.demo.controller.customer.CustomerResponseFilter;
import com.example.demo.controller.customer.v5.mapper.CustomerReponseFilterMapper;
import com.example.demo.controller.support.v2.GenericQueryControllerV2;
import com.example.demo.domain.Customer;
import com.example.demo.service.v2.CustomerQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/*
 * va mal los parametros del override pq no respeta los opcionales
 */
@RestController
@RequestMapping(value = "/api/v6/customer")
@Tag(name = "Customer v6", description = "The Customer API")
public class CustomerQueryControllerV6 extends GenericQueryControllerV2<Customer, Long, CustomerResponseFilter> {

    public CustomerQueryControllerV6(final CustomerQueryService service, final CustomerReponseFilterMapper mapper) {
        super(service, mapper);
    }

    @Operation(summary = "Get By Id Customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = CustomerResponseFilter.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content) })
    @Override
    public ResponseEntity<MappingJacksonValue> findById(@PathVariable final Long id, WebRequest request,
            @RequestParam(required = false, value = FIELDS) String... fields) {
        return super.findById(id, request, fields);
    }

    @Override
    @Operation(summary = "Get All Customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = CustomerResponseFilter.class))) })
    public ResponseEntity<MappingJacksonValue> findAll(
            @RequestParam(required = false, value = FIELDS) final String... fields) {
        return super.findAll(fields);
    }

    @Override
    @Operation(summary = "Search Customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = CustomerResponseFilter.class))) })
    public ResponseEntity<MappingJacksonValue> matching(
            @RequestParam(name = PAGE, defaultValue = DEFAULT_VALUE_PAGE) Integer page,
            @RequestParam(name = SIZE, defaultValue = DEFAULT_VALUE_SIZE) Integer size,
            @RequestParam(required = false, name = FILTER) String filter,
            @RequestParam(required = false, name = SORT, defaultValue = DEFAULT_VALUE_SORT) final String sort,
            @RequestParam(required = false, value = FIELDS) final String... fields) {
        return super.matching(page, size, filter, sort, fields);
    }

}
