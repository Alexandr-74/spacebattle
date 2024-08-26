package ru.spacebattle.commands.handler;

import ru.spacebattle.commands.factory.IoC;
import ru.spacebattle.entities.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static ru.spacebattle.enums.CommandEnum.HARD_STOP;
import static ru.spacebattle.enums.CommandEnum.SOFT_STOP;

public class ParallelCommandHandler {

    private BlockingQueue<Command> commandBlockingQueue;
    private List<Command> doneCommands;

    private  ExecutorService executor;

    public ParallelCommandHandler(BlockingQueue<Command> commandBlockingQueue) {
        this.commandBlockingQueue = commandBlockingQueue;
    }

    public void execute() {

        doneCommands = new ArrayList<>();
        executor = Executors.newCachedThreadPool();

        executor.execute(() -> {
            try {

                while (!commandBlockingQueue.isEmpty()) {
                    Command command = commandBlockingQueue.take();
                    if (HARD_STOP.equals(command.getCommandEnum())) {
                        System.out.println("HARD_STOP");
                        commandBlockingQueue.clear();
                    } else if (SOFT_STOP.equals(command.getCommandEnum())) {
                        System.out.println("SOFT_STOP");
                        commandBlockingQueue = new LinkedBlockingQueue<>(commandBlockingQueue);
                    } else {
                        IoC.resolve(command.getCommandEnum().name(), command.getParams());
                    }
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
}
