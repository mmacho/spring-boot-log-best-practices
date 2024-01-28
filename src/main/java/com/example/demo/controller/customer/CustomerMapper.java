package com.example.demo.controller.customer;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.example.demo.controller.Response;
import com.example.demo.domain.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

	CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

	Customer toDomain(CustomerCreateRequest request);

	Customer toDomain(CustomerUpdateRequest request);

	CustomerResponse toResponse(Customer domain);

	List<CustomerResponse> toResponse(List<Customer> domains);

	Set<CustomerResponse> toResponse(Set<Customer> domains);

	default Page<CustomerResponse> toResponse(Page<Customer> pages) {
		List<CustomerResponse> list = toResponse(pages.getContent());
		return new PageImpl<>(list, pages.getPageable(), pages.getTotalElements());
	}

	default Response<CustomerResponse> toGenericResponse(Customer domain) {
		return Response.success(toResponse(domain));
	}

	default Response<List<CustomerResponse>> toGenericResponse(List<Customer> lists) {
		return Response.success(toResponse(lists));
	}

	default Response<Page<CustomerResponse>> toGenericResponse(Page<Customer> pages) {
		return Response.success(toResponse(pages));
	}

}
