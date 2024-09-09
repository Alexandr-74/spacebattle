package ru.spacebattle.exception;

public class LowFuelException extends DefaultException {
    public LowFuelException(String message, int code) {
        super(message, code);
    }
}
