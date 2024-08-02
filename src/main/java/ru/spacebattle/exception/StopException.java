package ru.spacebattle.exception;

public class StopException extends DefaultException {

    public StopException(String message, int code) {
        super(message, code);
    }
}
