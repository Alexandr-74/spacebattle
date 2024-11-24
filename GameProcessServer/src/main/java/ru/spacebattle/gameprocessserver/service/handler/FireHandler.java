package ru.spacebattle.gameprocessserver.service.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.Uuid;
import org.springframework.stereotype.Component;
import ru.spacebattle.dto.InterpretCommandResponseDto;
import ru.spacebattle.entities.UObject;
import ru.spacebattle.enums.CommandEnum;
import ru.spacebattle.enums.UObjectProperties;
import ru.spacebattle.gameprocessserver.kafka.producer.BaseProducer;
import ru.spacebattle.gameprocessserver.service.GameResourcesService;
import ru.spacebattle.measures.Vector;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static ru.spacebattle.constants.GameConstants.GAME_FIELD_SIZE;
import static ru.spacebattle.constants.GameConstants.MISSILE_VELOCITY;

@Slf4j
@Component
@RequiredArgsConstructor
public class FireHandler implements CommandsHandler {

    private final GameResourcesService gameResourcesService;
    private final BaseProducer baseProducer;

    @Override
    public CommandEnum getCommand() {
        return CommandEnum.FIRE;
    }

    @Override
    public void execute(UObject uObject, Object... params) {

        UUID gameId = UUID.fromString(uObject.getProperties().get(UObjectProperties.GAME_ID).toString());
        Map<UUID, UObject> uObjects = gameResourcesService.getGameResources(gameId);
        Vector missilePosition = new Vector((Vector) uObject.getProperties().get(UObjectProperties.POSITION));
        Vector missileDirection = (Vector) uObject.getProperties().get(UObjectProperties.DIRECTION);
        UObject hittedObject = null;
        if (missileDirection.x != 0) {
            while (checkIsOnField(missilePosition, missileDirection)) {
                tryMove(missilePosition, missileDirection);
                sendCurrentState(gameId);
                hittedObject = checkIsHitTheTarget(uObjects, missilePosition);
                if (Objects.nonNull(hittedObject)) {
                    break;
                }
            }
        } else if (missileDirection.y != 0) {
            while (checkIsOnField(missilePosition, missileDirection)) {
                tryMove(missilePosition, missileDirection);
                sendCurrentState(gameId);
                hittedObject = checkIsHitTheTarget(uObjects, missilePosition);
                if (Objects.nonNull(hittedObject)) {
                    break;
                }
            }
        }

        if (Objects.nonNull(hittedObject)) {
            UUID objectId = UUID.fromString(hittedObject.getProperties().get(UObjectProperties.ID).toString());
            log.info("Remove object {} in game {}", objectId, gameId);
            gameResourcesService.removeResource(gameId, objectId);
        }
        sendCurrentState(gameId);
    }

    private UObject checkIsHitTheTarget(Map<UUID, UObject> uObjects, Vector missilePosition) {
        UObject hittedObject = null;
        for (UObject uObject : uObjects.values()) {
            Vector uObjectPosition = (Vector) uObject.getProperties().get(UObjectProperties.POSITION);
            if (Objects.equals(uObjectPosition, missilePosition)) {
                hittedObject = uObject;
                log.info("ПОПАДАНИЕ!!!!");
            }
        }

        return hittedObject;
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
            Thread.sleep(MISSILE_VELOCITY);
        } catch (InterruptedException e) {
            log.error("Что то не так с перемещением объекта {}", e.getMessage(), e);
        }
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
