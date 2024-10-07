package ru.spacebattle.authorizationserver.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateGameRequest {
    private String username;

    private String password;

    private List<SignInRequest> players;
}
