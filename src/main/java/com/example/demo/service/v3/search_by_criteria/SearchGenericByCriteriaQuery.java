package com.example.demo.service.v3.search_by_criteria;

import com.example.demo.service.support.Query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public abstract class SearchGenericByCriteriaQuery implements Query {

    private final Integer page;
    
    private final Integer size;
    
    private final String filter;
    
    private final String sort;

}
