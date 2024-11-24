package ru.spacebattle.gameserver.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.spacebattle.dto.CreateGameRequest;
import ru.spacebattle.dto.CreateGameResponse;
import ru.spacebattle.gameserver.service.GameService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/create-game")
    public CreateGameResponse createGameToken(@RequestHeader String authorization, @RequestBody CreateGameRequest createGameRequest) {
        return gameService.createGame(createGameRequest);
    }

}
