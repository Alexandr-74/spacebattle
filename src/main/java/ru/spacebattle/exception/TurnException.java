package ru.spacebattle.exception;

public class TurnException extends DefaultException {
    public TurnException(String message, int code) {
        super(message, code);
    }
}
