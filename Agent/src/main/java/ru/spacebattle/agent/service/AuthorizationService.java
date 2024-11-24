package ru.spacebattle.agent.service;

import ru.spacebattle.agent.dto.JwtAuthenticationResponse;
import ru.spacebattle.agent.dto.ResponseMessage;
import ru.spacebattle.agent.dto.SignUpRequest;
import ru.spacebattle.dto.CreateGameRequest;

public interface AuthorizationService {

    ResponseMessage signUp(SignUpRequest signUpRequest);
    ResponseMessage signIn(SignUpRequest signUpRequest);
    JwtAuthenticationResponse createGame(CreateGameRequest createGameRequest);
}
