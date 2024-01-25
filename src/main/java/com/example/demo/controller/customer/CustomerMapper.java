package com.example.demo.controller.customer;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.example.demo.domain.Customer;

@Mapper
public interface CustomerMapper {

	CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

	Customer toDomain(CustomerCreateRequest request);

	Customer toDomain(CustomerUpdateRequest request);

	CustomerResponse toResponse(Customer domain);

	default List<CustomerResponse> toListResponse(List<Customer> domains) {
		List<CustomerResponse> list = new ArrayList<>();
		domains.forEach(d -> list.add(toResponse(d)));
		return list;
	}

	default Page<CustomerResponse> toPageResponse(Page<Customer> pages) {
		List<CustomerResponse> list = toListResponse(pages.getContent());
		return new PageImpl<>(list, pages.getPageable(), pages.getTotalElements());
	}
}
