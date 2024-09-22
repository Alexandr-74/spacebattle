package ru.spacebattle.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InterpretCommandResponseDto {
    private String message;

    @JsonCreator
    public InterpretCommandResponseDto(@JsonProperty("message") Object message) {
        this.message = String.valueOf(message);
    }
}
