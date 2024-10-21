package ru.spacebattle.entities;

import ru.spacebattle.enums.CommandEnum;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

public class Command {

    private final UUID id;

    private final CommandEnum action;

    private final UUID gameId;

    private final UUID uObjectId;

    private Object[] params;
    private String message;

    private boolean done = false;
    private int tries;

    public Command(UUID id, UUID gameId, UUID uObjectId, CommandEnum action,  Object... params) {
        this.id = id;
        this.gameId = gameId;
        this.uObjectId = uObjectId;
        this.params = params;
        this.action = action;
        tries = 0;
    }

    public CommandEnum getAction() {
        return action;
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

    public UUID getGameId() {
        return gameId;
    }

    public UUID getuObjectId() {
        return uObjectId;
    }

    public UUID getId() {
        return id;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
