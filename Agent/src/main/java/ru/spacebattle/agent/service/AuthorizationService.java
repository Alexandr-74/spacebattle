package ru.spacebattle.agent.service;

import ru.spacebattle.agent.dto.CreateGameRequest;
import ru.spacebattle.agent.dto.JwtAuthenticationResponse;
import ru.spacebattle.agent.dto.ResponseMessage;
import ru.spacebattle.agent.dto.SignUpRequest;

public interface AuthorizationService {

    ResponseMessage signUp(SignUpRequest signUpRequest);

    JwtAuthenticationResponse createGame(CreateGameRequest createGameRequest);
}
