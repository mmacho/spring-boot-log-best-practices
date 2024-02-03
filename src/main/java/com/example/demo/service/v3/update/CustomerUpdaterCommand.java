package com.example.demo.service.v3.update;

import com.example.demo.service.v3.create.CreateCustomerCommand;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class CustomerUpdaterCommand extends CreateCustomerCommand {

    @NonNull
    private final Long id;

}
