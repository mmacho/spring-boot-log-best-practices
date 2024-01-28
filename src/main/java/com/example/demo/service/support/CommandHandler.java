package com.example.demo.service.support;

public interface CommandHandler<T extends Command, R extends Response> {
    R handle(T command);
}
