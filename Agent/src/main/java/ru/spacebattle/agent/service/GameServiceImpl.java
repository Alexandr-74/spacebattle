package ru.spacebattle.agent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spacebattle.agent.connector.GameServerConnector;
import ru.spacebattle.dto.CreateGameRequest;
import ru.spacebattle.dto.CreateGameResponse;
import ru.spacebattle.dto.InterpretCommandResponseDto;
import ru.spacebattle.entities.UObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final Map<UUID, Map<UUID, UObject>> gameObjectMap = new HashMap<>();

    private final AuthorizationService authorizationService;
    private final GameServerConnector gameServerConnector;

    @Override
    public CreateGameResponse createGame(CreateGameRequest createGameRequest) {
        return ofNullable(authorizationService.createGame(createGameRequest))
                .map(authToken -> {
                    CreateGameResponse response = gameServerConnector.createGame(authToken, createGameRequest);
                    gameObjectMap.put(response.getGameId(), response.getUObjects());
                    return response;
                })
                .orElseThrow(() -> new RuntimeException("Ошибка создания игры"));
    }

    @Override
    public void saveGameData(InterpretCommandResponseDto interpretCommandResponseDto) {
        gameObjectMap.put(interpretCommandResponseDto.getGameId(), interpretCommandResponseDto.getUObjects());
    }

    @Override
    public Map<UUID, UObject> getGameData(UUID gameId) {
        return gameObjectMap.get(gameId);
    }
}
