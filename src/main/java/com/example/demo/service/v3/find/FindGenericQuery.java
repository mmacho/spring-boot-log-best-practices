package com.example.demo.service.v3.find;

import java.io.Serializable;

import com.example.demo.service.support.Query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public abstract class FindGenericQuery<ID extends Serializable> implements Query {

    private final ID id;
}
