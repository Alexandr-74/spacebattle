package ru.spacebattle.gameserver.service;

import ru.spacebattle.dto.InterpretCommandRequestDto;

public interface InterpretCommandService {

    void interpretCommand(InterpretCommandRequestDto interpretCommandRequestDto);
}
