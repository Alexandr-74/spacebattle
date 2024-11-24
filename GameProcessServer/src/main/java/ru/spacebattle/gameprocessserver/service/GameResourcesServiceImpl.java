package ru.spacebattle.gameprocessserver.service;

import org.springframework.stereotype.Service;
import ru.spacebattle.commands.handler.ParallelCommandHandler;
import ru.spacebattle.dto.CreateGameResourcesRequest;
import ru.spacebattle.entities.Command;
import ru.spacebattle.entities.UObject;
import ru.spacebattle.enums.UObjectProperties;
import ru.spacebattle.measures.Vector;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class GameResourcesServiceImpl implements GameResourcesService {

    private final Map<UUID, Map<UUID, UObject>> gameObjectMap = new HashMap<>();
    private final Map<UUID, ParallelCommandHandler> gameHandlerMap = new HashMap<>();

    @Override
    public void createGameResources(CreateGameResourcesRequest createGameResourcesRequest) {
        createGameResourcesRequest.getUObjectMap().values().stream().forEach(uObject -> {
            Object positionObj = uObject.getProperties().get(UObjectProperties.POSITION);
            if (positionObj instanceof LinkedHashMap<?, ?>) {
                Vector position = new Vector((LinkedHashMap<Integer, Integer>) uObject.getProperties().get(UObjectProperties.POSITION));
                uObject.setProperty(UObjectProperties.POSITION, position);
            }

            Object directionObj = uObject.getProperties().get(UObjectProperties.DIRECTION);
            if (directionObj instanceof LinkedHashMap<?, ?>) {
                Vector direction = new Vector((LinkedHashMap<Integer, Integer>) uObject.getProperties().get(UObjectProperties.DIRECTION));
                uObject.setProperty(UObjectProperties.DIRECTION, direction);
            }
        });
        gameObjectMap.put(createGameResourcesRequest.getGameId(), createGameResourcesRequest.getUObjectMap());
        initGameProcess(createGameResourcesRequest.getGameId());
        System.out.printf("Game with id %s created", createGameResourcesRequest.getGameId());
    }

    @Override
    public List<UObject> getUObjectsByGame(UUID gameId) {
        return (List<UObject>) gameObjectMap.get(gameId).values();
    }

    @Override
    public UObject getUObjectByGameIdAndObjectId(UUID gameId, UUID uObjectId) {
        return gameObjectMap.get(gameId).get(uObjectId);
    }

    @Override
    public ParallelCommandHandler getGameHandlerByGame(UUID gameId) {
        return gameHandlerMap.get(gameId);
    }

    @Override
    public Map<UUID, UObject> getGameResources(UUID gameId) {
        return gameObjectMap.get(gameId);
    }

    @Override
    public void removeResource(UUID gameId, UUID uObjectId) {
        gameObjectMap.get(gameId).remove(uObjectId);
    }

    @Override
    public void removeGame(UUID gameId) {
        gameObjectMap.remove(gameId);
    }

    private void initGameProcess(UUID gameId) {
        BlockingQueue<Command> queue = new LinkedBlockingQueue<>();

        ParallelCommandHandler parallelCommandHandler = new ParallelCommandHandler(queue, gameId);
        parallelCommandHandler.execute();
        gameHandlerMap.put(gameId, parallelCommandHandler);
    }

}
