package ru.spacebattle.commands.handler;

import ru.spacebattle.commands.factory.IoC;
import ru.spacebattle.entities.Command;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;

public class ParallelCommandHandler {

    private final BlockingQueue<Command> commandBlockingQueue;
    private final BlockingQueue<Command> doneCommands;
    private final ExecutorService executor;
    private Predicate<Object> executing;

    public ParallelCommandHandler(BlockingQueue<Command> commandBlockingQueue) {
        this.commandBlockingQueue = commandBlockingQueue;
        executor = Executors.newCachedThreadPool();
        doneCommands = new LinkedBlockingQueue<>();
    }

    public void execute() {

        executing = (any) -> true;

        executor.execute(() -> {
            try {

                while (executing.test(1)) {
                    Command command = commandBlockingQueue.take();
                    System.out.println("Выполняется команда" + command.getCommandEnum());
                    try {
                        IoC.resolve(command.getCommandEnum().name(), command.getParams());
                    } catch (Exception e) {
                        Command errorCommand = new Command(command.getCommandEnum(), command.getParams());
                        errorCommand.setMessage(e.getMessage());
                        doneCommands.add(errorCommand);
                        throw e;
                    }

                    command.setDone(true);
                    doneCommands.add(command);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public BlockingQueue<Command> getDoneCommands() {
        return doneCommands;
    }

    ExecutorService getExecutor() {
        return executor;
    }

    public BlockingQueue<Command> getQueue() {

        return commandBlockingQueue;
    }

    public boolean getExecuting() {
        return executing.test(1);
    }

    public void setExecuting(Predicate<Object> executing) {
        this.executing = executing;
    }
}
