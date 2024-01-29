package com.example.demo.service.support;

import lombok.NonNull;

public interface QueryHandler<Q extends Query, R extends Response> {
    R handle(@NonNull final Q query);
}
