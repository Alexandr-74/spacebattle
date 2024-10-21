package ru.spacebattle.gameserver.entity;

import lombok.Data;
import ru.spacebattle.entities.Command;
import ru.spacebattle.enums.CommandEnum;


public class CommandResponse extends Command {

    public CommandResponse(CommandEnum commandEnum, Object... params) {
        super(commandEnum, params);
    }
}
