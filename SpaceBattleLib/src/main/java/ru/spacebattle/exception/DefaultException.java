package ru.spacebattle.exception;

public class DefaultException extends RuntimeException {
    private int code;

    public DefaultException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
