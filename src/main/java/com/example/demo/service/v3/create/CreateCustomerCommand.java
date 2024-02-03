package com.example.demo.service.v3.create;

import com.example.demo.service.support.Command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class CreateCustomerCommand implements Command {

    private String firstName;

    private String lastName;

    private String emailId;
}
