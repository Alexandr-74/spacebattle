package ru.spacebattle.gameprocessserver.service;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.spacebattle.dto.CreateGameResourcesRequest;
import ru.spacebattle.entities.UObject;
import ru.spacebattle.enums.UObjectProperties;
import ru.spacebattle.measures.Vector;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class GameResourceServiceTest {

    @InjectMocks
    private GameResourcesServiceImpl gameResourcesService;

    private CreateGameResourcesRequest createGameResourcesRequest;

    private UUID gameId = UUID.randomUUID();
    private UObject uObject;

    @BeforeEach
    void setUp() {
        Map<UUID, UObject> uObjectMap = new HashMap<>();
        uObjectMap.put(UUID.randomUUID(), new UObject());
        createGameResourcesRequest = new CreateGameResourcesRequest(uObjectMap, gameId);

    }

    @Test
    void givenCreateGameResourcesRequest_whenGameInit_thenSuccess() {
        Assertions.assertDoesNotThrow(() -> {
            gameResourcesService.createGameResources(createGameResourcesRequest);
            gameResourcesService.getGameHandlerByGame(gameId).setExecuting((any)->false);
        });
    }
}
