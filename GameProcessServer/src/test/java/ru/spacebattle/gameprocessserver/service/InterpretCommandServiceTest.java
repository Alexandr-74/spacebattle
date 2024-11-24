package ru.spacebattle.gameprocessserver.service;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.spacebattle.commands.handler.ParallelCommandHandler;
import ru.spacebattle.entities.Command;
import ru.spacebattle.entities.UObject;
import ru.spacebattle.enums.CommandEnum;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InterpretCommandServiceTest {

    @InjectMocks
    private InterpretCommandServiceImpl interpretCommandService;

    @Mock
    private GameResourcesService gameResourcesService;

    private Command command;
    private UUID gameId = UUID.randomUUID();
    private UUID uObjectId = UUID.randomUUID();
    private ParallelCommandHandler parallelCommandHandler;
    private UObject uObject;

    @BeforeEach
    void setUp() {
        command = new Command();
        command.setAction(CommandEnum.MOVE);
        command.setParams(new Object[] {"5"});
        command.setGameId(gameId);
        command.setUObjectId(uObjectId);
        parallelCommandHandler = mock(ParallelCommandHandler.class);
        uObject = mock(UObject.class);
    }

    @Test
    void givenCommandFromKafka_whenProcessCommand_thenSuccess() {
        when(gameResourcesService.getGameHandlerByGame(any())).thenReturn(parallelCommandHandler);
        when(gameResourcesService.getUObjectByGameIdAndObjectId(any(), any())).thenReturn(uObject);
        when(parallelCommandHandler.getQueue()).thenReturn(new LinkedBlockingQueue<>());
        Assertions.assertDoesNotThrow(() -> {
            interpretCommandService.interpretCommand(command);
        });
    }
}
