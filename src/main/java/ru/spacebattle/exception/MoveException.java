package ru.spacebattle.exception;

public class MoveException extends DefaultException {
    public MoveException(String message, int code) {
        super(message, code);
    }
}
