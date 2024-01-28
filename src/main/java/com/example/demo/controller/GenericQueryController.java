package com.example.demo.controller;

import java.io.Serializable;
import java.time.Duration;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.example.demo.controller.customer.BaseController;
import com.example.demo.controller.support.Responses;
import com.example.demo.domain.BaseEntity;
import com.example.demo.service.v2.GenericQueryService;

import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Validated
public class GenericQueryController<T extends BaseEntity, ID extends Serializable, R extends Responses>
                extends BaseController {

        private final GenericQueryService<T, ID> service;

        private final ResponseMapper<R, T> mapper;

        @GetMapping
        @Timed
        public ResponseEntity<Response<List<R>>> findAll() {
                final List<T> entities = service.getAll();
                return entities.isEmpty() ? ResponseEntity.noContent().build()
                                : ResponseEntity.ok().body(mapper.toGenericResponse(entities));
        }

        @GetMapping(value = MACHING)
        @Timed
        public ResponseEntity<Response<Page<R>>> matching(
                        @RequestParam(name = PAGE, defaultValue = DEFAULT_VALUE_PAGE) Integer page,
                        @RequestParam(name = SIZE, defaultValue = DEFAULT_VALUE_SIZE) Integer size,
                        @RequestParam(required = false, name = FILTER) String filter,
                        @RequestParam(required = false, name = SORT, defaultValue = DEFAULT_VALUE_SORT) String sort) {
                final Page<T> entities = service.matching(page, size, filter, sort);
                return entities.isEmpty() ? ResponseEntity.noContent().build()
                                : ResponseEntity.ok().body(mapper.toGenericResponse(entities));
        }

        @SuppressWarnings("unchecked")
        @GetMapping(value = ID, params = FIELDS)
        @Timed
        public ResponseEntity<Response<R>> findById(@PathVariable final ID id, WebRequest request,
                        @RequestParam(required = false, value = FIELDS) String... fields) {
                final T entity = service.findbyId(id);
                final String etag = String.valueOf(entity.getVersion());
                if (request.checkNotModified(etag)) {
                        return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                                        .cacheControl(CacheControl.maxAge(Duration.ofDays(1)))
                                        .build();
                }
                final R response = mapper.toResponse(entity);
                final MappingJacksonValue filterResponse = filter(response, BaseResponse.FIELDS_FILTER,
                                fields);
                return ResponseEntity.ok().cacheControl(CacheControl.maxAge(Duration.ofDays(1))).eTag(etag)
                                .body(Response.success((R) filterResponse.getValue()));
        }

}
