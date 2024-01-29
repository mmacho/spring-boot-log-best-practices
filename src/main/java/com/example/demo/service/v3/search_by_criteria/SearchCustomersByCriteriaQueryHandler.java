package com.example.demo.service.v3.search_by_criteria;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Customer;
import com.example.demo.service.support.QueryHandler;
import com.example.demo.service.v3.CustomerResponse;
import com.example.demo.service.v3.CustomersPageReponse;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor
public class SearchCustomersByCriteriaQueryHandler
        implements QueryHandler<SearchCustomersByCriteriaQuery, CustomersPageReponse> {

    private final CustomersByCriteriaSearcher searcher;

    @Override
    public CustomersPageReponse handle(@NonNull final SearchCustomersByCriteriaQuery query) {
        final Page<Customer> entities = searcher.search(query.getPage(), query.getSize(), query.getFilter(),
                query.getSort());
        final Page<CustomerResponse> responses = entities.map(entity -> CustomerResponse.builder()
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .emailId(entity.getEmailId())
                .build());
        return CustomersPageReponse.builder().customers(responses).build();
    }

}
