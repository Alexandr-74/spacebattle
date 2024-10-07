package ru.spacebattle.gameserver.service;

import ru.spacebattle.dto.CreateGameResponse;

public interface GameService {

    CreateGameResponse createGame(String authorization);
}
