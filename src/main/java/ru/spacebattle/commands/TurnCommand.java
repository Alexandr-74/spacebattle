package ru.spacebattle.commands;

import ru.spacebattle.entities.UObject;
import ru.spacebattle.measures.Vector;

import static ru.spacebattle.enums.UObjectProperties.DIRECTION;
import static ru.spacebattle.enums.UObjectProperties.DIRECTION_NUMBERS;

public class TurnCommand implements Turnable {

    public final int directionNumbers = 8;
    private final UObject turnableObject;
    public Vector directionVector;

    public TurnCommand(UObject turnObject) {
        this.turnableObject = turnObject;
        directionVector = new Vector(0, 0);
    }

    @Override
    public Vector turn(int direction) throws Exception {
        if (direction < 0 || direction > directionNumbers) {
            throw new Exception(String.format("Ошибка поворота объекта, направление должно больше или равно 0 и меньше %s", directionNumbers));
        }

        directionVector = new Vector(xDirectionCalculation(direction), yDirectionCalculation(direction));

        turnableObject.setProperty(DIRECTION, directionVector);
        turnableObject.setProperty(DIRECTION_NUMBERS, directionNumbers);

        return directionVector;
    }

    private int xDirectionCalculation(int direction) {
        return (int) Math.round(Math.cos(Math.toRadians(direction * 360 / directionNumbers)));
    }

    private int yDirectionCalculation(int direction) {
        return (int) Math.round(Math.sin(Math.toRadians(direction * 360 / directionNumbers)));
    }
}
