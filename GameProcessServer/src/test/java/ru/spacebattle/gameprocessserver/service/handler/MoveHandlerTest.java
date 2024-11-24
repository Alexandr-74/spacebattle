package ru.spacebattle.gameprocessserver.service.handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.spacebattle.entities.UObject;
import ru.spacebattle.enums.UObjectProperties;
import ru.spacebattle.gameprocessserver.kafka.producer.BaseProducer;
import ru.spacebattle.gameprocessserver.service.GameResourcesServiceImpl;
import ru.spacebattle.measures.Vector;

import java.util.HashMap;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MoveHandlerTest {

    @InjectMocks
    private MoveHandler moveHandler;

    @Mock
    private GameResourcesServiceImpl gameResourcesService;
    @Mock
    private BaseProducer baseProducer;

    private UObject uObject;
    private UUID uObjectId = UUID.randomUUID();
    private UUID gameId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        uObject = new UObject();
        uObject.setProperty(UObjectProperties.POSITION, new Vector(0, 0));
        uObject.setProperty(UObjectProperties.DIRECTION, new Vector(1, 0));
        uObject.setProperty(UObjectProperties.ID, uObjectId);
        uObject.setProperty(UObjectProperties.GAME_ID, gameId);
        uObject.setProperty(UObjectProperties.PLAYER_ID, UUID.randomUUID());
    }

    @Test
    void givenMoveCommand_whenProcessCommand_thenSuccess() {
        when(gameResourcesService.getGameResources(any())).thenReturn(new HashMap<>());
        Assertions.assertDoesNotThrow(() ->{
            moveHandler.execute(uObject, new Object[]{"4"});
        });
    }
}
