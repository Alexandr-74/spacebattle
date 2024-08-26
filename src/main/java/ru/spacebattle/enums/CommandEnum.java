package ru.spacebattle.enums;

public enum CommandEnum {
    MOVE("move"),
    FIRE("fire"),
    TURN("turn"),
    STOP("stop"),
    UNDEFINED("undefined"),
    LOG("log"),

    HARD_STOP("hard_stop"),

    SOFT_STOP("soft_stop"),
    REPEATE("repeate");

    private final String value;

    CommandEnum(String value) {
        this.value = value;
    }
}
