package ru.spacebattle.agent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spacebattle.agent.connector.GameServerConnector;
import ru.spacebattle.dto.CreateGameRequest;
import ru.spacebattle.dto.CreateGameResponse;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final AuthorizationService authorizationService;
    private final GameServerConnector gameServerConnector;

    @Override
    public CreateGameResponse createGame(CreateGameRequest createGameRequest) {
        return ofNullable(authorizationService.createGame(createGameRequest))
                .map(authToken -> gameServerConnector.createGame(authToken, createGameRequest))
                .orElseThrow(() -> new RuntimeException("Ошибка создания игры"));
    }
}
