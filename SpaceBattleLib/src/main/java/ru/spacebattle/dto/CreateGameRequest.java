package ru.spacebattle.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateGameRequest {
    private String username;

    private String password;

    private List<UserPasswordDto> players;

    private int spaceshipCount;
}
