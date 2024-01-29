package com.example.demo.service.v3.delete;

import org.springframework.stereotype.Service;

import com.example.demo.service.support.CommandHandler;
import com.example.demo.service.v3.CustomerResponse;

import lombok.AllArgsConstructor;
import lombok.NonNull;

//TODO: Code smell: debería retornar void
@Service
@AllArgsConstructor
public class DeleteCustomerCommandHandler implements CommandHandler<DeleteCustomerCommand, CustomerResponse> {

    private final CustomerDeleter deleter;

    @Override
    public CustomerResponse handle(@NonNull final DeleteCustomerCommand command) {
        deleter.delete(command.getId());
        return null;
    }

}
