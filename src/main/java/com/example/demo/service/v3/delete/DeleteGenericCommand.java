package com.example.demo.service.v3.delete;

import java.io.Serializable;

import com.example.demo.service.support.Command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public abstract class DeleteGenericCommand<ID extends Serializable> implements Command {

    private final ID id;
}
