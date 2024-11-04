package ru.spacebattle.commands.handler;

import ru.spacebattle.commands.factory.IoC;
import ru.spacebattle.entities.Command;
import ru.spacebattle.enums.CommandEnum;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;

public class ParallelCommandHandler {

    private final BlockingQueue<Command> commandBlockingQueue;
    private final BlockingQueue<Command> doneCommands;
    private final ExecutorService executor;
    private BlockingQueue<Command> backUpQueueBlockingQueue;
    private Predicate<Object> executing;

    private CommandHandlerState currentState = new RunState();

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
                    currentState = currentState.executeCommand(commandBlockingQueue.take());
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

    public BlockingQueue<Command> getBackUpQueueBlockingQueue() {
        return backUpQueueBlockingQueue;
    }

    public void setBackUpQueueBlockingQueue(BlockingQueue<Command> backUpQueueBlockingQueue) {
        this.backUpQueueBlockingQueue = backUpQueueBlockingQueue;
    }

    interface CommandHandlerState {
        CommandHandlerState executeCommand(Command command);
    }

    private class RunState implements CommandHandlerState {

        @Override
        public CommandHandlerState executeCommand(Command command) {
            System.out.println("Выполняется команда " + command.getAction());
            try {
                Command result = IoC.resolve(command.getAction().name(), command.getParams());
                doneCommands.put(result);
            } catch (Exception e) {
                Command errorCommand = new Command(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), command.getAction(), command.getParams());
                errorCommand.setMessage(e.getMessage());
                doneCommands.add(errorCommand);
                System.err.println("Ошибка при выполнении команды: " +e.getMessage());
            }

            command.setDone(true);
            doneCommands.add(command);

            return switch (command.getAction()) {
                case HARD_STOP -> new HardStopState();
                case MOVE_TO -> new MoveToState();
                default -> new RunState();
            };
        }
    }

    private class MoveToState implements CommandHandlerState {

        @Override
        public CommandHandlerState executeCommand(Command command) {
            System.out.println("Перенос команды в очередь backup " + command.getAction());
            if (backUpQueueBlockingQueue != null) {
                try {
                    backUpQueueBlockingQueue.put(command);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            return switch (command.getAction()) {
                case HARD_STOP -> new HardStopState();
                case RUN -> new RunState();
                default -> new MoveToState();
            };
        }
    }

    private class HardStopState implements CommandHandlerState {

        @Override
        public CommandHandlerState executeCommand(Command command) {
            System.out.println("Обработка команд остановлена, режим HARD_STOP");

            return new HardStopState();
        }
    }
}
