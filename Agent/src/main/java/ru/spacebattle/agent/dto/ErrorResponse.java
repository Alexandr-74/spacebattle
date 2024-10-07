package ru.spacebattle.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse extends ResponseMessage {
    String errorMessage;
}
