package ru.spacebattle.exception;

import ru.spacebattle.enums.CommandEnum;

public class FireException extends DefaultException {

    public FireException(String message, int code) {
        super(message, code);
    }
}
