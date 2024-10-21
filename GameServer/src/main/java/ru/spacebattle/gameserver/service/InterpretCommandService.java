package ru.spacebattle.gameserver.service;

import ru.spacebattle.dto.InterpretCommandRequestDto;
import ru.spacebattle.entities.Command;

public interface InterpretCommandService {

    void interpretCommand(Command command);
}
