package ru.spacebattle.movement;

public class Turning {

    public static final byte directionNumbers = 8;

    public static int xDirectionCalculation(int direction) {
        return (int) Math.round(Math.cos(Math.toRadians(direction * 360/directionNumbers)));
    }
    public static int yDirectionCalculation(int direction) {
        return (int) Math.round(Math.sin(Math.toRadians(direction * 360/directionNumbers)));
    }
}
