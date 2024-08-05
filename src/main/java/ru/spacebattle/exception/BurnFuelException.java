package ru.spacebattle.exception;

public class BurnFuelException extends DefaultException {
    public BurnFuelException(String message, int code) {
        super(message, code);
    }
}
