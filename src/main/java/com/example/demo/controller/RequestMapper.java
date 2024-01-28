package com.example.demo.controller;

public interface RequestMapper<R, D> {
    
    D toDomain(R request);
	
}
