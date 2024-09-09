package ru.spacebattle.dto;

import lombok.Data;

import java.util.List;

@Data
public class InterpretCommandDto {

    private String commandName;

    private List<String> args;
}
