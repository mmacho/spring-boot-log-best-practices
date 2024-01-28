package com.example.demo.service.support;

public interface QueryHandler<Q extends Query, R extends Response> {
    R handle(Q query);
}
