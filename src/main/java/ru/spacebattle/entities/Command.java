package ru.spacebattle.entities;

import ru.spacebattle.enums.CommandEnum;

public class Command {

    private final CommandEnum commandEnum;
    private int tries;

    public Command(CommandEnum commandEnum) {
        this.commandEnum = commandEnum;
        tries = 0;
    }

    public CommandEnum getCommandEnum() {
        return commandEnum;
    }


    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

}
