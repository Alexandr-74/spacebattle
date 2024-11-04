package ru.spacebattle.agent.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.spacebattle.agent.dto.ResponseMessage;
import ru.spacebattle.agent.dto.SignUpRequest;
import ru.spacebattle.agent.service.AuthorizationService;
import ru.spacebattle.agent.service.GameService;
import ru.spacebattle.agent.service.InterpretCommandService;
import ru.spacebattle.dto.CreateGameRequest;
import ru.spacebattle.dto.CreateGameResponse;
import ru.spacebattle.dto.InterpretCommandRequestDto;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommandsController {

    private final InterpretCommandService interpretCommandService;
    private final AuthorizationService authorizationService;
    private final GameService gameService;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseMessage> signUp(@RequestBody SignUpRequest signUpRequest) {
        log.info("Auth request");
        return ResponseEntity.of(Optional.of(authorizationService.signUp(signUpRequest)));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ResponseMessage> signIn(@RequestBody SignUpRequest signUpRequest) {
        log.info("Auth request");
        return ResponseEntity.of(Optional.of(authorizationService.signIn(signUpRequest)));
    }


    @PostMapping("/create-game")
    public ResponseEntity<CreateGameResponse> createGame(@RequestBody CreateGameRequest createGameRequest) {
        log.info("Auth request");
        return ResponseEntity.of(Optional.of(gameService.createGame(createGameRequest)));
    }

    @PostMapping("/interpret-command")
    public ResponseEntity<String> addCommand(@RequestBody InterpretCommandRequestDto interpretCommandRequestDto) {
        return interpretCommandService.sendCommand(interpretCommandRequestDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.internalServerError().body("Ошбика отправки сообщения"));
    }
}
