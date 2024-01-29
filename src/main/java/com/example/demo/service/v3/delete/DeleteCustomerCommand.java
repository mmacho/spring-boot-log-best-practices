package com.example.demo.service.v3.delete;

public class DeleteCustomerCommand extends DeleteGenericCommand<Long> {
    
        public DeleteCustomerCommand(final Long id) {
            super(id);
        }
}
