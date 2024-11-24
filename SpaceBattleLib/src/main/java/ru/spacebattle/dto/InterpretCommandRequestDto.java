package ru.spacebattle.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class InterpretCommandRequestDto {

    @JsonProperty("gameId")
    private UUID gameId;
    @JsonProperty("uObjectId")
    private UUID uObjectId;
    @JsonProperty("commandsList")
    private List<InterpretCommandDto> commandsList;
}
