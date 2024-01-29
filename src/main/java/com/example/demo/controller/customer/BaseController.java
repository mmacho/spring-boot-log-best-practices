package com.example.demo.controller.customer;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.controller.Response;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.MediaType;

@RequestMapping(value = "/api", produces = { MediaType.APPLICATION_JSON_VALUE,
		MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_XML_VALUE })
public abstract class BaseController {

	public static final String SEARCH = "/search";

	public static final String MACHING = "/maching";

	public static final String ID = "/{id}";

	public static final String FIELDS = "fields";

	public static final String DEFAULT_VALUE_SIZE = "25";

	public static final String DEFAULT_VALUE_PAGE = "1";

	public static final String SORT = "sort";

	public static final String FILTER = "filter";

	public static final String SIZE = "size";

	public static final String PAGE = "page";

	public static final String DEFAULT_VALUE_SORT = "id,desc";

	protected BaseController() {

	}

	/**
	 * No se usa
	 * 
	 * @param responseToFilter
	 * @param filterName
	 * @param fields
	 * @return
	 */
	protected Response<MappingJacksonValue> filterResponse(Object responseToFilter, String filterName,
			String... fields) {
		MappingJacksonValue filteredResponse = new MappingJacksonValue(responseToFilter);
		SimpleFilterProvider filters = new SimpleFilterProvider().addFilter(filterName,
				SimpleBeanPropertyFilter.filterOutAllExcept(fields));
		filteredResponse.setFilters(filters);
		return Response.success(filteredResponse);
	}

	/**
	 * no se usa por el casteo ya que da warning
	 * 
	 * @param responseToFilter
	 * @param filterName
	 * @param fields
	 * @return
	 */
	protected <T> Response<MappingJacksonValue> filtering(T t, String filterName, String... fields) {
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(fields);
		FilterProvider filters = new SimpleFilterProvider().addFilter(filterName, filter);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(t);
		mappingJacksonValue.setFilters(filters);
		return Response.success(mappingJacksonValue);
	}

	/**
	 * el bueno
	 * 
	 * @param responseToFilter
	 * @param filterName
	 * @param fields
	 * @return
	 */
	protected <T> MappingJacksonValue filter(T t, String filterName, String... fields) {
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(fields);
		FilterProvider filters = new SimpleFilterProvider().addFilter(filterName, filter);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(t);
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}
}
