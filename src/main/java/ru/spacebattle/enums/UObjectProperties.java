package ru.spacebattle.enums;

public enum UObjectProperties {
    DIRECTION("Direction"),
    DIRECTION_NUMBERS("DirectionNumbers"),
    VELOCITY("Velocity"),
    TURN_VELOCITY_DELTA("TurnVelocityDelta"),
    FUEL_VOLUME("FuelVolume"),
    BURN_FUEL_VELOCITY("BurnFuelVelocity");

    private final String value;

    UObjectProperties(String value) {
        this.value = value;
    }
}
