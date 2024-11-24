package ru.spacebattle.gameserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.spacebattle.dto.CreateGameRequest;
import ru.spacebattle.dto.CreateGameResourcesRequest;
import ru.spacebattle.dto.CreateGameResponse;
import ru.spacebattle.dto.UserPasswordDto;
import ru.spacebattle.entities.UObject;
import ru.spacebattle.enums.UObjectProperties;
import ru.spacebattle.gameserver.connector.GameProcessConnector;
import ru.spacebattle.measures.Vector;

import java.util.*;

import static ru.spacebattle.constants.GameConstants.GAME_FIELD_SIZE;


@Slf4j
@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final Map<UUID, Map<UUID, UObject>> gameObjectMap = new HashMap<>();
    private final GameProcessConnector gameProcessConnector;

    @Override
    public CreateGameResponse createGame(CreateGameRequest createGameRequest) {
        UUID gameId = UUID.randomUUID();
        log.info("Game created {}", gameId);
        initGameResources(gameId, createGameRequest);
        gameProcessConnector.createGameResourcesRequest(new CreateGameResourcesRequest(gameObjectMap.get(gameId), gameId));
        return new CreateGameResponse(gameId, gameObjectMap.get(gameId));
    }


    private void initGameResources(UUID playId, CreateGameRequest createGameRequest) {
        Map<UUID, UObject> uObjectsMap = new HashMap<>();
        uObjectsMap.putAll(createGameObjects(createGameRequest.getSpaceshipCount(), createGameRequest.getUser(), playId));
        createGameRequest.getPlayers()
                .forEach(player -> uObjectsMap.putAll(createGameObjects(createGameRequest.getSpaceshipCount(), player, playId)));

        gameObjectMap.put(playId, uObjectsMap);
    }


    private Map<UUID, UObject> createGameObjects(int spaceShipCount, UserPasswordDto userData, UUID gameId) {
        Map<UUID, UObject> uObjectsMap = new HashMap<>();
        for (int i = 0; i < spaceShipCount; i++) {
            UObject uObject = new UObject();
            UUID uObjectId = UUID.randomUUID();
            Random random = new Random();
            uObject.setProperty(UObjectProperties.POSITION, new Vector(random.nextInt((GAME_FIELD_SIZE + 1) - 1) + 1, random.nextInt((GAME_FIELD_SIZE + 1) - 1) + 1));
            uObject.setProperty(UObjectProperties.DIRECTION, new Vector((int) Math.round(Math.random()), (int) Math.round(Math.random())));
            uObject.setProperty(UObjectProperties.ID, uObjectId);
            uObject.setProperty(UObjectProperties.GAME_ID, gameId);
            uObject.setProperty(UObjectProperties.PLAYER_ID, UUID.randomUUID());
            uObject.setProperty(UObjectProperties.PLAYER_NAME, userData.getUsername());
            log.info("UObject created {}", uObjectId);
            uObjectsMap.put(uObjectId, uObject);
        }

        return uObjectsMap;
    }
}
