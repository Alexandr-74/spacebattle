package ru.spacebattle.gameprocessserver.service;

import ru.spacebattle.entities.Command;

public interface InterpretCommandService {
    void interpretCommand(Command command);
}
