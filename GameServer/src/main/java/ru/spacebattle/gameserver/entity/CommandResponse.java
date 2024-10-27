package ru.spacebattle.gameserver.entity;

import ru.spacebattle.entities.Command;
import ru.spacebattle.enums.CommandEnum;

import java.util.UUID;


public class CommandResponse extends Command {

    public CommandResponse(CommandEnum commandEnum, Object... params) {
        super(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), commandEnum, params);
    }
}
