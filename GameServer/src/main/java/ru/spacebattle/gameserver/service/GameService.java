package ru.spacebattle.gameserver.service;

import ru.spacebattle.commands.handler.ParallelCommandHandler;
import ru.spacebattle.dto.CreateGameRequest;
import ru.spacebattle.dto.CreateGameResponse;
import ru.spacebattle.entities.UObject;

import java.util.Map;
import java.util.UUID;

public interface GameService {

    CreateGameResponse createGame(CreateGameRequest createGameRequest);
    ParallelCommandHandler getGame(UUID gameId);
    UObject getUObject(UUID gameId, UUID uObjectId);
}
