package ru.spacebattle.dto;


import lombok.Data;

import java.util.List;

@Data
public class InterpretCommandRequestDto {

    private long playId;

    private long uObjectId;

    private List<InterpretCommandDto> commandsList;
}
