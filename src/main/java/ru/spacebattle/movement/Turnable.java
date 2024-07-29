package ru.spacebattle.movement;

import ru.spacebattle.measures.Vector;

public interface Turnable {

    Vector turn(int direction) throws Exception;
}
