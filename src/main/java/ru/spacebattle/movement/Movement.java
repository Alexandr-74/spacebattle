package ru.spacebattle.movement;

import ru.spacebattle.entities.UObject;
import ru.spacebattle.measures.Vector;

public class Movement implements Movable {

    private UObject movableObject;
    public Vector position;

    public Movement(UObject movableObject) {
        this.movableObject = movableObject;
        position = new Vector(0,0);
    }

    @Override
    public Vector move(int velocity, int direction) throws Exception {

        if (position == null) {
            throw new Exception("Ошибка чтения позиции объекта");
        }

        if (velocity < 0) {
            throw new Exception("Ошибка получения скорости объекта, скорость должна быть выше 0");
        }

        if (direction < 0 || direction > Turning.directionNumbers) {
            throw new Exception(String.format("Ошибка поворота объекта, направление должно больше или равно 0 и меньше %s", Turning.directionNumbers));
        }

        movableObject.getProperties().put("Directon", direction);
        movableObject.getProperties().put("DirectonNumbers", Turning.directionNumbers);
        movableObject.getProperties().put("Velocity", velocity);

        Vector currentPosition = new Vector(
                velocity * Turning.xDirectionCalculation(direction),
                velocity * Turning.yDirectionCalculation(direction));


        position.x = position.x + currentPosition.x;
        position.y = position.y + currentPosition.y;
        return new Vector(position.x, position.y);
    }

    public Vector getPosition() {
        return position;
    }
}
