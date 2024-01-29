package com.example.demo.service.v3.create;

import com.example.demo.service.support.Command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class CreateCustomerCommand implements Command {

    private final String firstName;

    private final String lastName;

    private final String emailId;
}
