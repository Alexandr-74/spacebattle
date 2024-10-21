package ru.spacebattle.dto;


import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class InterpretCommandRequestDto {

    private UUID playId;

    private UUID uObjectId;

    private List<InterpretCommandDto> commandsList;
}
