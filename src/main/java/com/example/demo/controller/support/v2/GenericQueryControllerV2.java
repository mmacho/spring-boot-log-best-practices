package com.example.demo.controller.support.v2;

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

import com.example.demo.controller.BaseResponse;
import com.example.demo.controller.Response;
import com.example.demo.controller.ResponseMapper;
import com.example.demo.controller.customer.BaseController;
import com.example.demo.controller.support.Responses;
import com.example.demo.domain.BaseEntity;
import com.example.demo.service.v2.GenericQueryService;

import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@Validated
public class GenericQueryControllerV2<T extends BaseEntity<ID>, ID extends Serializable, R extends Responses>
                extends BaseController {

        @NonNull
        private final GenericQueryService<T, ID> service;
        @NonNull
        private final ResponseMapper<R, T> mapper;

        @GetMapping
        @Timed
        public ResponseEntity<MappingJacksonValue> findAll(
                        @RequestParam(required = false, value = FIELDS) String... fields) {
                final List<T> entities = service.getAll();
                final Response<List<R>> responses = mapper.toGenericResponse(entities);
                final MappingJacksonValue filterResponse = filter(responses, BaseResponse.FIELDS_FILTER,
                                fields);
                return entities.isEmpty() ? ResponseEntity.noContent().build()
                                : ResponseEntity.ok().body(filterResponse);
        }

        @GetMapping(value = MACHING)
        @Timed
        public ResponseEntity<MappingJacksonValue> matching(
                        @RequestParam(name = PAGE, defaultValue = DEFAULT_VALUE_PAGE) Integer page,
                        @RequestParam(name = SIZE, defaultValue = DEFAULT_VALUE_SIZE) Integer size,
                        @RequestParam(required = false, name = FILTER) String filter,
                        @RequestParam(required = false, name = SORT, defaultValue = DEFAULT_VALUE_SORT) String sort,
                        @RequestParam(required = false, value = FIELDS) String... fields) {
                final Page<T> entities = service.matching(page, size, filter, sort);
                final Response<Page<R>> responses = Response.success(mapper.toResponse(entities));
                final MappingJacksonValue filterResponse = filter(responses, BaseResponse.FIELDS_FILTER,
                                fields);
                return entities.isEmpty() ? ResponseEntity.noContent().build()
                                : ResponseEntity.ok().body(filterResponse);
        }

        @GetMapping(value = ID)
        @Timed
        public ResponseEntity<MappingJacksonValue> findById(@PathVariable final ID id, WebRequest request,
                        @RequestParam(required = false, value = FIELDS) String... fields) {
                final T entity = service.findbyId(id);
                final String eTag = String.valueOf(entity.getVersion());
                if (request.checkNotModified(eTag)) {
                        return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                                        .cacheControl(CacheControl.maxAge(Duration.ofDays(1)))
                                        .build();
                }
                final Response<R> response = Response.success(mapper.toResponse(entity));
                final MappingJacksonValue filterResponse = filter(response, BaseResponse.FIELDS_FILTER,
                                fields);
                return ResponseEntity.ok().cacheControl(CacheControl.maxAge(Duration.ofDays(1))).eTag(eTag)
                                .body(filterResponse);
        }

}
