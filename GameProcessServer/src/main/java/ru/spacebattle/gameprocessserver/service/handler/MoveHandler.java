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

import static ru.spacebattle.constants.GameConstants.GAME_FIELD_SIZE;
import static ru.spacebattle.constants.GameConstants.SPASESHIP_VELOCITY;

@Slf4j
@Component
@RequiredArgsConstructor
public class MoveHandler implements CommandsHandler {

    private final BaseProducer baseProducer;
    private final GameResourcesService gameResourcesService;

    @Override
    public CommandEnum getCommand() {
        return CommandEnum.MOVE;
    }

    @Override
    public void execute(UObject uObject, Object... params) {

        try {

            Vector position = (Vector) uObject.getProperties().get(UObjectProperties.POSITION);
            Vector direction = (Vector) uObject.getProperties().get(UObjectProperties.DIRECTION);
            UUID gamaId = UUID.fromString(uObject.getProperties().get(UObjectProperties.GAME_ID).toString());
            int length = Integer.parseInt(params[0].toString());
            int i = 0;

            log.info("Перемещяю объект {} на {}", uObject, length);
            if (direction.x != 0) {
                while (i < length && checkIsOnField(position, direction)) {
                    tryMove(position, direction);
                    sendCurrentState(gamaId);
                    i++;
                }
            } else if (direction.y != 0) {
                while (i < length && checkIsOnField(position, direction)) {
                    tryMove(position, direction);
                    sendCurrentState(gamaId);
                    i++;
                }
            }

            uObject.setProperty(UObjectProperties.POSITION, position);
        } catch (Exception e) {
            log.error("Ошибка при потпытке выполнения движения {}", e.getMessage(), e);
        }
    }

    private boolean checkIsOnField(Vector position, Vector direction) {
        Vector res = new Vector(position);
        res.plus(direction);

        if (res.y > GAME_FIELD_SIZE || res.y < 0) {
            return false;
        }

        return res.x <= GAME_FIELD_SIZE && res.x >= 0;
    }

    private void tryMove(Vector position, Vector direction) {
        try {
            position.plus(direction);
            log.info("Объект перемещен {}", position);
            Thread.sleep(SPASESHIP_VELOCITY);
        } catch (InterruptedException e) {
            log.error("Что то не так с перемещением объекта {}", e.getMessage(), e);
        }
    }

    private void sendCurrentState(UUID gameId) {
        InterpretCommandResponseDto res;

        res = new InterpretCommandResponseDto("Выполнена команда MOVE", gameId, gameResourcesService.getGameResources(gameId));

        baseProducer.sendCommand(UUID.randomUUID(), res);
    }
}
