package ru.spacebattle.entities;

import ru.spacebattle.enums.CommandEnum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Command {

    private final CommandEnum commandEnum;

    private final Object[] params;
    private String message;

    private boolean done = false;
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

    public String getStringParams() {
        return params != null ? Arrays.stream(params).map(String::valueOf).collect(Collectors.joining(", ")) : "no params";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
