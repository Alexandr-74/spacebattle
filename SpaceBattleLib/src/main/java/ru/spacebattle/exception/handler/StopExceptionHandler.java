package ru.spacebattle.exception.handler;

import ru.spacebattle.entities.Command;
import ru.spacebattle.enums.CommandEnum;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

public class StopExceptionHandler extends DefaultExceptionHandler {


    BlockingQueue<Command> queue;

    public StopExceptionHandler(BlockingQueue<Command> queue) {
        super();
        this.queue = queue;
    }

    @Override
    public void handle(Command cmd, Exception exception) throws InterruptedException {
        queue.put(new Command(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), CommandEnum.REPEATE));
    }
}
