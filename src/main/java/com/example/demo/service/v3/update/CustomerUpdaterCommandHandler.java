package com.example.demo.service.v3.update;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Customer;
import com.example.demo.service.support.CommandHandler;
import com.example.demo.service.v3.CustomerResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerUpdaterCommandHandler implements CommandHandler<CustomerUpdaterCommand, CustomerResponse> {

    private final CustomerUpdater updater;

    @Override
    public CustomerResponse handle(CustomerUpdaterCommand command) {
        final Customer entity = updater
                .update(command.getId(),
                        new Customer(command.getFirstName(), command.getLastName(), command.getEmailId()));
        return CustomerResponse.builder()
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .emailId(entity.getEmailId())
                .build();
    }

}
