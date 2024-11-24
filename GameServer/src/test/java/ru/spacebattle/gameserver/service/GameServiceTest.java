package ru.spacebattle.gameserver.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.spacebattle.dto.CreateGameRequest;
import ru.spacebattle.dto.UserPasswordDto;
import ru.spacebattle.gameserver.connector.GameProcessConnector;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @InjectMocks
    private GameServiceImpl gameService;

    @Mock
    private GameProcessConnector gameProcessConnector;

    private CreateGameRequest createGameRequest;

    @BeforeEach
    void setUp() {
        createGameRequest = new CreateGameRequest();
        UserPasswordDto player1 = new UserPasswordDto();
        player1.setPassword("!23");
        player1.setUsername("Test1");
        UserPasswordDto player2 = new UserPasswordDto();
        player2.setPassword("!23");
        player2.setUsername("Test2");
        createGameRequest.setUser(player1);
        createGameRequest.setPlayers(List.of(player2));
        createGameRequest.setSpaceshipCount(3);
    }


    @Test
    void givenCreateGameRequest_whenInitGameResources_thenSuccess() {
        Assertions.assertDoesNotThrow(() -> {
            gameService.createGame(createGameRequest);
        });
    }
}
