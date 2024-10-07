package ru.spacebattle.gameserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.spacebattle.dto.CreateGameResponse;
import ru.spacebattle.gameserver.service.security.JwtService;

import java.util.UUID;

import static ru.spacebattle.gameserver.config.filter.JwtAuthenticationFilter.BEARER_PREFIX;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final JwtService jwtService;

    @Override
    public CreateGameResponse createGame(String authorization) {
        var jwt = authorization.substring(BEARER_PREFIX.length());
        UUID gameId = jwtService.extractGameId(jwt);
        log.info("Game created {}", gameId);
        return new CreateGameResponse(gameId);
    }
}
