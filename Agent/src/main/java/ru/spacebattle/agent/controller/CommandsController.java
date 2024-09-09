package ru.spacebattle.agent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.spacebattle.agent.service.InterpretCommandService;
import ru.spacebattle.dto.InterpretCommandRequestDto;

@Controller
@RequiredArgsConstructor
public class CommandsController {

    private final InterpretCommandService interpretCommandService;

    @PostMapping("/interpret-command")
    public ResponseEntity<String> addCommand(@RequestBody InterpretCommandRequestDto interpretCommandRequestDto) {
        return interpretCommandService.sendCommand(interpretCommandRequestDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.internalServerError().body("Ошбика отправки сообщения"));
    }
}
