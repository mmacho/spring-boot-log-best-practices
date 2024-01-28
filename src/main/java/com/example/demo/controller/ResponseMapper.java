package com.example.demo.controller;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public interface ResponseMapper<R, D> {

	R toResponse(D domain);

	List<R> toResponse(List<D> domains);

	Set<R> toResponse(Set<D> domains);

	default Page<R> toResponse(Page<D> pages) {
		List<R> list = toResponse(pages.getContent());
		return new PageImpl<>(list, pages.getPageable(), pages.getTotalElements());
	}

	default Response<R> toGenericResponse(D domain) {
		return Response.success(toResponse(domain));
	}

	default Response<List<R>> toGenericResponse(List<D> lists) {
		return Response.success(toResponse(lists));
	}

	default Response<Page<R>> toGenericResponse(Page<D> pages) {
		return Response.success(toResponse(pages));
	}
}
