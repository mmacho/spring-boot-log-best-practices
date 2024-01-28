package com.example.demo.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {

    private boolean succeeded ;
    
    private T data;

    public static <T> Response<T> empty() {
        return success(null);
    }

    public static <T> Response<T> success(T data) {
        return Response.<T>builder()
        .data(data)
        .succeeded(Boolean.TRUE)
        .build();
    }

    public static <T> Response<T> error(T data) {
        return Response.<T>builder()
        .data(data)
        .succeeded(Boolean.FALSE)
        .build();
    }
    
}
