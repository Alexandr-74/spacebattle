package ru.spacebattle.entities;

import ru.spacebattle.enums.CommandEnum;

public class Command {

    private final CommandEnum commandEnum;

    private final Object[] params;
    private int tries;

    public Command(CommandEnum commandEnum, Object... params) {
        this.params = params;
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

    public Object[] getParams() {
        return params;
    }

}
