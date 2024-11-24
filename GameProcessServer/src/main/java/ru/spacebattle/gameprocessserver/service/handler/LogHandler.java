package ru.spacebattle.gameprocessserver.service.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.spacebattle.dto.InterpretCommandResponseDto;
import ru.spacebattle.entities.UObject;
import ru.spacebattle.enums.CommandEnum;
import ru.spacebattle.enums.UObjectProperties;
import ru.spacebattle.gameprocessserver.kafka.producer.BaseProducer;
import ru.spacebattle.gameprocessserver.service.GameResourcesService;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogHandler implements CommandsHandler {

    private final BaseProducer baseProducer;
    private final GameResourcesService gameResourcesService;

    @Override
    public CommandEnum getCommand() {
        return CommandEnum.LOG;
    }

    @Override
    public void execute(UObject uObject, Object... params) {
        UUID gameId = UUID.fromString(uObject.getProperties().get(UObjectProperties.GAME_ID).toString());
        log.info("Logging objects {}", uObject);
        sendCurrentState(gameId);
    }


    private void sendCurrentState(UUID gameId) {
        InterpretCommandResponseDto res;

        res = new InterpretCommandResponseDto(
                "Выполнена команда MOVE",
                gameId,
                gameResourcesService.getGameResources(gameId)
        );

        baseProducer.sendCommand(UUID.randomUUID(), res);
    }
}
