package ru.spacebattle.exception.handler;

import ru.spacebattle.entities.Command;
import ru.spacebattle.enums.CommandEnum;

import java.util.concurrent.BlockingQueue;

public class StopExceptionHandler extends DefaultExceptionHandler {


    BlockingQueue<Command> queue;

    public StopExceptionHandler(BlockingQueue<Command> queue) {
        super();
        this.queue = queue;
    }

    @Override
    public void handle(Command cmd, Exception exception) throws InterruptedException {
        queue.put(new Command(CommandEnum.REPEATE));
    }
}
