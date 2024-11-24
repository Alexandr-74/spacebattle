package ru.spacebattle.enums;

public enum UObjectProperties {
    DIRECTION("Direction"),
    DIRECTION_NUMBERS("DirectionNumbers"),
    VELOCITY("Velocity"),

    POSITION("Position"),

    IS_STOPPED("IsStopped"),
    TURN_VELOCITY_DELTA("TurnVelocityDelta"),
    FUEL_VOLUME("FuelVolume"),
    BURN_FUEL_VELOCITY("BurnFuelVelocity"),
    ID("id"),

    GAME_ID("gameId"),
    PLAYER_ID("playerId"),
    PLAYER_NAME("playerName");

    private final String value;

    UObjectProperties(String value) {
        this.value = value;
    }
}
