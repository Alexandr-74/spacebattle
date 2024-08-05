package ru.spacebattle.enums;

public enum CommandEnum {
    MOVE("move"),
    FIRE("fire"),
    TURN("turn"),
    STOP("stop"),
    UNDEFINED("undefined"),
    LOG("log"),
    REPEATE("repeate");

    private final String value;

    CommandEnum(String value) {
        this.value = value;
    }
}
