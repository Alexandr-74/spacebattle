package ru.spacebattle.movement;

import ru.spacebattle.entities.UObject;
import ru.spacebattle.measures.Vector;

public class Movement implements Movable {

    public Vector position;
    public Turn turn;
    private final UObject movableObject;

    public Movement(UObject movableObject) {
        this.movableObject = movableObject;
        turn = new Turn(movableObject);
        position = new Vector(0, 0);
    }

    @Override
    public Vector move(int velocity, int direction) throws Exception {

        if (position == null) {
            throw new Exception("Ошибка чтения позиции объекта");
        }

        if (velocity < 0) {
            throw new Exception("Ошибка получения скорости объекта, скорость должна быть выше 0");
        }

        movableObject.getProperties().put("Velocity", velocity);
        Vector directionVector = turn.turn(direction);

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
