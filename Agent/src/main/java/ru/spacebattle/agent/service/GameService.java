package ru.spacebattle.agent.service;

import ru.spacebattle.dto.CreateGameRequest;
import ru.spacebattle.dto.CreateGameResponse;

public interface GameService {
    CreateGameResponse createGame(CreateGameRequest createGameRequest);
}
