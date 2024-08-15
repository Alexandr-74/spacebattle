package ru.spacebattle.commands;

import ru.spacebattle.exception.BurnFuelException;

public interface BurnFuel {
    void burnFuel(int burnFuelVolume) throws BurnFuelException;
}
