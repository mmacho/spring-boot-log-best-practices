package com.example.demo.service.v3.create;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Customer;
import com.example.demo.service.support.CommandHandler;
import com.example.demo.service.v3.CustomerResponse;

import lombok.AllArgsConstructor;
import lombok.NonNull;

/**
 * No sacar el objeto customer de base de datos es para que nadie tenga la tentación de modificarlo en otras capas.
 * Handler for {@link CreateCustomerCommand}.
 */
@Service
@AllArgsConstructor
public class CreateCustomerCommandHandler implements CommandHandler<CreateCustomerCommand, CustomerResponse> {

    private final CustomerCreator creator;

    @Override
    public CustomerResponse handle(@NonNull final CreateCustomerCommand command) {
        final Customer entity = creator
                .create(new Customer(command.getFirstName(), command.getLastName(), command.getEmailId()));
        return CustomerResponse.builder()
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .emailId(entity.getEmailId())
                .build();
    }

}
