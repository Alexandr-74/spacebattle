package ru.spacebattle.agent.service;


import ru.spacebattle.dto.InterpretCommandRequestDto;

import java.util.Optional;

public interface InterpretCommandService {

    Optional<String> sendCommand(InterpretCommandRequestDto interpretCommandRequestDto);
}
