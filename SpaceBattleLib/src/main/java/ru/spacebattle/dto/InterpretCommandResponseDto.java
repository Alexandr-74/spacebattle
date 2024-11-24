package ru.spacebattle.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.spacebattle.entities.UObject;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterpretCommandResponseDto {
    @JsonProperty("message")
    private String message;

    @JsonProperty("gameId")
    private UUID gameId;

    @JsonProperty("uObjects")
    private Map<UUID, UObject> uObjects;
}
