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
import ru.spacebattle.measures.Vector;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class TurnHandler implements CommandsHandler {

    private final BaseProducer baseProducer;
    private final GameResourcesService gameResourcesService;

    @Override
    public CommandEnum getCommand() {
        return CommandEnum.TURN;
    }

    @Override
    public void execute(UObject uObject, Object... params) {
        int x = Integer.parseInt(params[0].toString());
        int y = Integer.parseInt(params[1].toString());
        UUID gamaId = UUID.fromString(uObject.getProperties().get(UObjectProperties.GAME_ID).toString());

        uObject.setProperty(UObjectProperties.DIRECTION, new Vector(x, y));
        sendCurrentState(gamaId);
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
