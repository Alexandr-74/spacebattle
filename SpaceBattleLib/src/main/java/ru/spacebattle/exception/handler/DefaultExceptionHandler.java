package ru.spacebattle.exception.handler;

import ru.spacebattle.entities.Command;

public class DefaultExceptionHandler implements ExceptionHandler {

    public DefaultExceptionHandler() {
    }

    @Override
    public void handle(Command cmd, Exception exception) throws InterruptedException {
        System.out.printf("Залогировано исключение команды %s: %s%n", cmd.getAction(), exception.getMessage());
    }
}
