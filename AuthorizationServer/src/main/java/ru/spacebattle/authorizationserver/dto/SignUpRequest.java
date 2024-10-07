package ru.spacebattle.authorizationserver.dto;

import lombok.Data;

@Data
public class SignUpRequest {

    private String username;

    private String password;
}