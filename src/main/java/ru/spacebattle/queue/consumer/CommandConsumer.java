package ru.spacebattle.queue.consumer;

import ru.spacebattle.entities.Command;

import java.util.concurrent.BlockingQueue;

public interface CommandConsumer {

    void readFromQ(BlockingQueue<Command> queue) throws InterruptedException;
}
