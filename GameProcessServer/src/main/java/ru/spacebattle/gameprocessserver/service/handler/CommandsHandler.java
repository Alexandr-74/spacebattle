package ru.spacebattle.gameprocessserver.service.handler;

import ru.spacebattle.entities.UObject;
import ru.spacebattle.enums.CommandEnum;

public interface CommandsHandler {

    void execute(UObject uObject, Object... params);

    CommandEnum getCommand();
}
