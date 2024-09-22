package ru.spacebattle.commands;

import ru.spacebattle.measures.Vector;

public interface Turnable {

    Vector turn(int direction) throws Exception;
}
