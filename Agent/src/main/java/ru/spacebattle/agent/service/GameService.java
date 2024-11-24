package ru.spacebattle.agent.service;

import ru.spacebattle.dto.CreateGameRequest;
import ru.spacebattle.dto.CreateGameResponse;
import ru.spacebattle.dto.InterpretCommandResponseDto;
import ru.spacebattle.entities.UObject;

import java.util.Map;
import java.util.UUID;

public interface GameService {
    CreateGameResponse createGame(CreateGameRequest createGameRequest);

    void saveGameData(InterpretCommandResponseDto interpretCommandResponseDto);

    Map<UUID, UObject> getGameData(UUID gameId);
}
