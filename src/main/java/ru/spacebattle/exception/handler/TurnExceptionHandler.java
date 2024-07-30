package ru.spacebattle.exception.handler;

import ru.spacebattle.entities.Command;

import java.util.concurrent.BlockingQueue;

public class TurnExceptionHandler extends DefaultExceptionHandler {

    BlockingQueue<Command> queue;

    public TurnExceptionHandler(BlockingQueue<Command> queue) {
        super();
        this.queue = queue;
    }

    @Override
    public void handle(Command cmd, Exception exception) throws InterruptedException {

        if (cmd.getTries() > 2) {
            System.out.println("Приостановка повторного выброса исключения");
        } else {
            System.out.println("Повторный выброс исключения");
            cmd.setTries(cmd.getTries() + 1);
            queue.put(cmd);
        }
    }
}
