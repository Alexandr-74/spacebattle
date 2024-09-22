package ru.spacebattle.commands;

import ru.spacebattle.entities.UObject;
import ru.spacebattle.exception.TurnException;
import ru.spacebattle.measures.Vector;

import static java.util.Optional.ofNullable;
import static ru.spacebattle.enums.UObjectProperties.TURN_VELOCITY_DELTA;

public class ChangeVelocityTurnCommand {

    private final UObject uObject;
    private final TurnCommand turnCommand;
    private final ChangeVelocityCommand changeVelocityCommand;

    private Vector directionVector;

    public ChangeVelocityTurnCommand(UObject uObject) {
        this.uObject = uObject;
        turnCommand = new TurnCommand(uObject);
        changeVelocityCommand = new ChangeVelocityCommand(uObject);
        directionVector = new Vector(1, 0);
    }

    public Vector turn(int direction) throws Exception {
        int turnVelocityDelta = (int) ofNullable(uObject.getProperties().get(TURN_VELOCITY_DELTA))
                .orElseThrow(() -> new TurnException("Ошибка поворота: не указано изменение скорости", 404));
        changeVelocityCommand.changeVelocity(turnVelocityDelta);
        Vector turnVector = turnCommand.turn(direction);
        changeVelocityCommand.changeVelocity(-turnVelocityDelta);
        directionVector = turnVector;

        return directionVector;
    }

    public Vector getDirection() {
        return directionVector;
    }
}
