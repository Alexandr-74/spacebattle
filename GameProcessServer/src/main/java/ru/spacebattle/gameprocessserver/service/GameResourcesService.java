package ru.spacebattle.gameprocessserver.service;

import ru.spacebattle.commands.handler.ParallelCommandHandler;
import ru.spacebattle.dto.CreateGameResourcesRequest;
import ru.spacebattle.entities.UObject;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface GameResourcesService {

    void createGameResources(CreateGameResourcesRequest createGameResourcesRequest);
    List<UObject> getUObjectsByGame(UUID gameId);
    UObject getUObjectByGameIdAndObjectId(UUID gameId, UUID uObjectId);
    ParallelCommandHandler getGameHandlerByGame(UUID gameId);
    Map<UUID, UObject> getGameResources(UUID gameId);

    void removeResource(UUID gameId, UUID uObjectId);
    void removeGame(UUID gameId);
}
