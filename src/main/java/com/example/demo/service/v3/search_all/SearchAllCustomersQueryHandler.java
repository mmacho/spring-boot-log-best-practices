package com.example.demo.service.v3.search_all;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Customer;
import com.example.demo.service.support.QueryHandler;
import com.example.demo.service.v3.CustomerResponse;
import com.example.demo.service.v3.CustomersResponse;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor
public class SearchAllCustomersQueryHandler implements QueryHandler<SearchAllCustomersQuery, CustomersResponse> {

    private final AllCustomersSearcher searcher;

    @Override
    public CustomersResponse handle(@NonNull final SearchAllCustomersQuery query) {
        final List<Customer> entities = searcher.search();
        final List<CustomerResponse> responses = entities.stream()
                .map(entity -> CustomerResponse.builder()
                        .firstName(entity.getFirstName())
                        .lastName(entity.getLastName())
                        .emailId(entity.getEmailId())
                        .build())
                .collect(Collectors.toCollection(ArrayList::new));
        return CustomersResponse.builder().customers(responses).build();
    }
}
