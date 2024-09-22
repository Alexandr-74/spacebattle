package ru.spacebattle.exception.handler;

import ru.spacebattle.entities.Command;

public interface ExceptionHandler {

    void handle(Command cmd, Exception exception) throws InterruptedException;
}
