package ru.spacebattle.commands;

import ru.spacebattle.entities.UObject;
import ru.spacebattle.exception.TurnException;
import ru.spacebattle.measures.Vector;

import static java.util.Optional.ofNullable;
import static ru.spacebattle.enums.UObjectProperties.DIRECTION;
import static ru.spacebattle.enums.UObjectProperties.VELOCITY;

public class MovementCommand implements Movable {

    private final UObject movableObject;
    public Vector position;

    public MovementCommand(UObject movableObject) {
        this.movableObject = movableObject;
        position = new Vector(0, 0);
    }

    @Override
    public Vector move(int velocity) throws Exception {

        if (position == null) {
            throw new Exception("Ошибка чтения позиции объекта");
        }

        if (velocity < 0) {
            throw new Exception("Ошибка получения скорости объекта, скорость должна быть выше 0");
        }

        Vector directionVector = (Vector) ofNullable(movableObject.getProperties().get(DIRECTION))
                .orElseThrow(() -> new TurnException("Отсутствует направление движения", 404));
        movableObject.setProperty(VELOCITY, velocity);

        Vector currentPosition = new Vector(
                velocity * directionVector.x,
                velocity * directionVector.y);


        position.x = position.x + currentPosition.x;
        position.y = position.y + currentPosition.y;
        return new Vector(position.x, position.y);
    }

    public Vector getPosition() {
        return position;
    }
}
