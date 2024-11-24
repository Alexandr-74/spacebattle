package ru.spacebattle.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateGameRequest {
    private UserPasswordDto user;

    private List<UserPasswordDto> players;

    private int spaceshipCount;
}
