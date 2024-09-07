package ru.spacebattle.commands.handler;

import ru.spacebattle.commands.factory.IoC;
import ru.spacebattle.entities.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Predicate;

public class ParallelCommandHandler {

    private BlockingQueue<Command> commandBlockingQueue;
    private List<Command> doneCommands;
    private  ExecutorService executor;
    private Predicate<Object> executing;

    public ParallelCommandHandler(BlockingQueue<Command> commandBlockingQueue) {
        this.commandBlockingQueue = commandBlockingQueue;
        executor = Executors.newCachedThreadPool();
        doneCommands = new ArrayList<>();
    }

    public void execute() {

        executing = (any) -> true;

        executor.execute(() -> {
            try {

                while (executing.test(1)) {
                    Command command = commandBlockingQueue.take();

                    IoC.resolve(command.getCommandEnum().name(), command.getParams());

                    doneCommands.add(command);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    List<Command> getDoneCommands() {
         return doneCommands;
    }

    ExecutorService getExecutor() {
        return executor;
    }

    BlockingQueue<Command> getQueue() {
        return commandBlockingQueue;
    }

    void setExecuting(Predicate<Object> executing) {
        this.executing = executing;
    }
}
