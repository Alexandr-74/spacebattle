package ru.spacebattle.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessResponse extends ResponseMessage {
    String message;
}
