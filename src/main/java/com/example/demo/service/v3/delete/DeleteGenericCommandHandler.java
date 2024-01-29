package com.example.demo.service.v3.delete;

import java.io.Serializable;

import com.example.demo.domain.BaseEntity;
import com.example.demo.service.support.CommandHandler;
import com.example.demo.service.support.Response;
import com.example.demo.service.support.v2.GenericDeleter;

import lombok.AllArgsConstructor;

import lombok.NonNull;

@AllArgsConstructor
public abstract class DeleteGenericCommandHandler<T extends BaseEntity<ID>, ID extends Serializable, R extends Response>
        implements CommandHandler<DeleteGenericCommand<ID>, R> {

    private final GenericDeleter<T, ID> deleter;

    @Override
    public R handle(@NonNull final DeleteGenericCommand<ID> command) {
        deleter.delete(command.getId());
        return null;
    }

}
