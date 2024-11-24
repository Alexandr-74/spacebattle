package ru.spacebattle.gameprocessserver.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spacebattle.dto.CreateGameResourcesRequest;
import ru.spacebattle.gameprocessserver.service.GameResourcesService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class GameProcessServerController {

    private final GameResourcesService gameResourcesService;

    @PostMapping("/initialize-game-resources")
    public ResponseEntity<String> createGameToken(@RequestBody CreateGameResourcesRequest gameResourcesRequest) {
        gameResourcesService.createGameResources(gameResourcesRequest);

        return ResponseEntity.ok("Created");
    }

}
