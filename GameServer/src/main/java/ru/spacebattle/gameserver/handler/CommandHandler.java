package ru.spacebattle.gameserver.handler;

import ru.spacebattle.entities.Command;
import ru.spacebattle.enums.CommandEnum;

public interface CommandHandler {
    Command handle(Command command);

    CommandEnum getCommandEnum();
}
