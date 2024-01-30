package com.example.demo.controller.support.v1;

import java.io.Serializable;
import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.controller.RequestMapper;
import com.example.demo.controller.Response;
import com.example.demo.controller.ResponseMapper;
import com.example.demo.controller.customer.BaseController;
import com.example.demo.controller.support.Request;
import com.example.demo.controller.support.Responses;
import com.example.demo.domain.BaseEntity;
import com.example.demo.service.v2.GenericCommandService;

import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import lombok.NonNull;

/*
 * 
 * code smell multilples mapper
 */
@AllArgsConstructor
@Validated
public class GenericCommandController<T extends BaseEntity<ID>, ID extends Serializable, CREQ extends Request, UREQ extends Request, RES extends Responses>
        extends BaseController {

    @NonNull
    private final GenericCommandService<T, ID> service;
    @NonNull
    private final RequestMapper<CREQ, T> reqCreateMapper;
    @NonNull
    private final RequestMapper<UREQ, T> reqUpdateMapper;
    @NonNull
    private final ResponseMapper<RES, T> resMapper;

    @PostMapping
    @Timed
    public ResponseEntity<Response<RES>> create(@Valid @RequestBody final CREQ request) {
        final T entity = service.create(reqCreateMapper.toDomain(request));
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(ID)
                .buildAndExpand(entity.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(location)
                .body(resMapper.toGenericResponse(entity));

    }

    @PutMapping(value = ID)
    @Timed
    public ResponseEntity<Response<RES>> update(@PathVariable final ID id,
            @Valid @RequestBody final UREQ request) {
        final T entity = service.update(id, reqUpdateMapper.toDomain(request));
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(ID)
                .buildAndExpand(entity.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.OK).location(location).body(resMapper.toGenericResponse(entity));
    }

    @DeleteMapping(value = ID)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable final ID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
