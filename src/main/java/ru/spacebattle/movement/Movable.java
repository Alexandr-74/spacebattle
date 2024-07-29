package ru.spacebattle.movement;

import ru.spacebattle.measures.Vector;

public interface Movable {

    Vector move(int velocity, int directionNumber) throws Exception;
}
