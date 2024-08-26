package ru.spacebattle.commands;

import ru.spacebattle.measures.Vector;

public interface Movable {

    Vector move(int velocity) throws Exception;
    Vector getPosition();
    Integer getVelocity();
}
