package ru.spacebattle.authorizationserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spacebattle.authorizationserver.dto.CreateGameRequest;
import ru.spacebattle.authorizationserver.dto.JwtAuthenticationResponse;
import ru.spacebattle.authorizationserver.dto.SignInRequest;
import ru.spacebattle.authorizationserver.dto.SignUpRequest;
import ru.spacebattle.authorizationserver.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse createGame(CreateGameRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);

        List<String> players = Stream.concat(request.getPlayers().stream().map(SignInRequest::getUsername), Stream.of(request.getUsername())).toList();

        userService.getAllByUsernames(players)
                        .forEach(usr -> {
                            usr.setGametoken(jwt);
                            userService.save(usr);
                        });


        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse getGame(SignInRequest request) {
        return new JwtAuthenticationResponse(userService.getByUsername(request.getUsername()).getGametoken());
    }
}