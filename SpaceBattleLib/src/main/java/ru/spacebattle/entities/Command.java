package ru.spacebattle.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.spacebattle.enums.CommandEnum;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class Command {

    private UUID id;
    private CommandEnum action;
    private UUID gameId;
    private UUID uObjectId;
    private Object[] params;
    private String message;
    private boolean done = false;
    private int tries;

    public Command(UUID id, UUID gameId, UUID uObjectId, CommandEnum action,  List<String> params) {
        this.id = id;
        this.gameId = gameId;
        this.uObjectId = uObjectId;
        this.params = params.toArray();
        this.action = action;
        tries = 0;
    }

    public Command(UUID id, UUID gameId, UUID uObjectId, CommandEnum action) {
        this.id = id;
        this.gameId = gameId;
        this.uObjectId = uObjectId;
        this.action = action;
        tries = 0;
    }


    public Command(UUID id, UUID gameId, UUID uObjectId, CommandEnum action, Object... params) {
        this.id = id;
        this.gameId = gameId;
        this.uObjectId = uObjectId;
        this.params = params;
        this.action = action;
        tries = 0;
    }

    public String getStringParams() {
        return params != null ? Arrays.stream(params).map(String::valueOf).collect(Collectors.joining(", ")) : "no params";
    }
}
