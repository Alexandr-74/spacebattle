package ru.spacebattle.movement;

import ru.spacebattle.entities.UObject;
import ru.spacebattle.measures.Vector;

public class Turn implements Turnable {

    public final int directionNumbers = 8;
    public Vector directionVector;
    private final UObject turnableObject;

    public Turn(UObject turnObject) {
        this.turnableObject = turnObject;
        directionVector = new Vector(0, 0);
    }

    @Override
    public Vector turn(int direction) throws Exception {
        if (direction < 0 || direction > directionNumbers) {
            throw new Exception(String.format("Ошибка поворота объекта, направление должно больше или равно 0 и меньше %s", directionNumbers));
        }

        directionVector = new Vector(xDirectionCalculation(direction), yDirectionCalculation(direction));

        turnableObject.getProperties().put("Directon", directionVector);
        turnableObject.getProperties().put("DirectonNumbers", directionNumbers);

        return directionVector;
    }

    private int xDirectionCalculation(int direction) {
        return (int) Math.round(Math.cos(Math.toRadians(direction * 360 / directionNumbers)));
    }

    private int yDirectionCalculation(int direction) {
        return (int) Math.round(Math.sin(Math.toRadians(direction * 360 / directionNumbers)));
    }
}
