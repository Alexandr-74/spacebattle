package ru.spacebattle.commands;

import ru.spacebattle.entities.UObject;
import ru.spacebattle.exception.BurnFuelException;
import ru.spacebattle.exception.LowFuelException;

import static java.util.Optional.ofNullable;
import static ru.spacebattle.enums.UObjectProperties.FUEL_VOLUME;

public class CheckFuelCommand implements CheckFuel {

    private final UObject uObject;

    public CheckFuelCommand(UObject uObject) {
        this.uObject = uObject;
    }

    @Override
    public void check() throws LowFuelException, BurnFuelException {
        int fuelVolume = (int) ofNullable(uObject.getProperties().get(FUEL_VOLUME))
                .orElseThrow(() -> new BurnFuelException("Не установлен параметр FuelVolume", 404));

        if (fuelVolume < 1) {
            throw new LowFuelException("Для совершения действия не достаточно топлива", 400);
        }
    }
}
