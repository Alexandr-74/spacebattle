package ru.spacebattle.agent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spacebattle.agent.connector.AuthorizationConnector;
import ru.spacebattle.agent.dto.JwtAuthenticationResponse;
import ru.spacebattle.agent.dto.ResponseMessage;
import ru.spacebattle.agent.dto.SignUpRequest;
import ru.spacebattle.dto.CreateGameRequest;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final AuthorizationConnector authorizationConnector;
    @Override
    public ResponseMessage signUp(SignUpRequest signUpRequest) {
        return authorizationConnector.signUp(signUpRequest);
    }
    @Override
    public ResponseMessage signIn(SignUpRequest signUpRequest) {
        return authorizationConnector.signIn(signUpRequest);
    }

    @Override
    public JwtAuthenticationResponse createGame(CreateGameRequest createGameRequest) {
        return authorizationConnector.createGame(createGameRequest);
    }
}
