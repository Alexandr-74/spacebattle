package ru.spacebattle.commands;

import ru.spacebattle.exception.DefaultException;

public interface ChangeVelocity {

    void changeVelocity(int velocityDelta) throws DefaultException;
}
