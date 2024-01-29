package com.example.demo.service.support;

import lombok.NonNull;

public interface CommandHandler<T extends Command, R extends Response> {
    R handle(@NonNull final T command);
}
