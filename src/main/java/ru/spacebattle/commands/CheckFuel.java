package ru.spacebattle.commands;

import ru.spacebattle.exception.BurnFuelException;
import ru.spacebattle.exception.LowFuelException;

public interface CheckFuel {

    void check() throws LowFuelException, BurnFuelException;
}
