package ru.spacebattle.authorizationserver.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spacebattle.authorizationserver.dto.JwtAuthenticationResponse;
import ru.spacebattle.authorizationserver.dto.SignInRequest;
import ru.spacebattle.authorizationserver.dto.SignUpRequest;
import ru.spacebattle.authorizationserver.service.AuthenticationService;
import ru.spacebattle.dto.CreateGameRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;


    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody SignInRequest request) {
        return authenticationService.signIn(request);
    }

    @PostMapping("/create-game-token")
    public JwtAuthenticationResponse createGameToken(@RequestBody CreateGameRequest request) {
        return authenticationService.createGame(request);
    }

    @PostMapping("/get-game-token")
    public JwtAuthenticationResponse getGameToken(@RequestBody SignInRequest request) {
        return authenticationService.getGame(request);
    }
}