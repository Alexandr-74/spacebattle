package ru.spacebattle.commands;

import ru.spacebattle.entities.UObject;
import ru.spacebattle.exception.BurnFuelException;

import static java.util.Optional.ofNullable;
import static ru.spacebattle.enums.UObjectProperties.FUEL_VOLUME;

public class BurnFuelCommand implements BurnFuel {

    private final UObject uObject;

    public BurnFuelCommand(UObject uObject) {
        this.uObject = uObject;
    }

    @Override
    public void burnFuel(int burnFuelVolume) throws BurnFuelException {
        int currentFuelVolume = (int) ofNullable(uObject.getProperties().get(FUEL_VOLUME))
                .orElseThrow(() -> new BurnFuelException("Не установлен параметр FuelVolume", 404));
        uObject.setProperty(FUEL_VOLUME, currentFuelVolume - burnFuelVolume);
    }
}
