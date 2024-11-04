package ru.spacebattle.gameserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.spacebattle.commands.handler.ParallelCommandHandler;
import ru.spacebattle.dto.CreateGameRequest;
import ru.spacebattle.dto.CreateGameResponse;
import ru.spacebattle.entities.Command;
import ru.spacebattle.entities.UObject;
import ru.spacebattle.gameserver.service.security.JwtService;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static ru.spacebattle.gameserver.config.filter.JwtAuthenticationFilter.BEARER_PREFIX;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final Map<UUID, ParallelCommandHandler> gamesMap = new HashMap<>();
    private final Map<UUID, Map<UUID, UObject>> gameObjectMap = new HashMap<>();
    private final JwtService jwtService;

    @Override
    public CreateGameResponse createGame(CreateGameRequest createGameRequest) {
        UUID gameId = UUID.randomUUID();
        log.info("Game created {}", gameId);
        initGame(gameId, createGameRequest);
        return new CreateGameResponse(gameId);
    }

    @Override
    public ParallelCommandHandler getGame(UUID gameId) {
        return gamesMap.get(gameId);
    }

    @Override
    public UObject getUObject(UUID gameId, UUID uObjectId) {
        return gameObjectMap.get(gameId).get(uObjectId);
    }

    private void initGame(UUID playId, CreateGameRequest createGameRequest) {
        BlockingQueue<Command> queue = new LinkedBlockingQueue<>();

        ParallelCommandHandler parallelCommandHandler = new ParallelCommandHandler(queue);
        parallelCommandHandler.execute();
        gamesMap.put(playId, parallelCommandHandler);

        Map<UUID, UObject> uObjectsMap = new HashMap<>();
        uObjectsMap.putAll(createGameObjects(createGameRequest.getSpaceshipCount()));
        createGameRequest.getPlayers()
                .forEach(player -> uObjectsMap.putAll(createGameObjects(createGameRequest.getSpaceshipCount())));

        gameObjectMap.put(playId, uObjectsMap);
    }


    Map<UUID, UObject> createGameObjects(int spaceShipCount) {
        Map<UUID, UObject> uObjectsMap = new HashMap<>();
        for (int i = 0; i < spaceShipCount; i++) {
            UObject uObject = new UObject();
            UUID uObjectId = UUID.randomUUID();
            log.info("UObject created {}", uObjectId);
            uObjectsMap.put(UUID.randomUUID(), uObject);
        }

        return uObjectsMap;
    }
}
