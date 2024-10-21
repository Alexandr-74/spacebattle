package ru.spacebattle.queue.consumer;

import ru.spacebattle.entities.Command;
import ru.spacebattle.enums.CommandEnum;
import ru.spacebattle.exception.handler.*;
import ru.spacebattle.queue.executer.CommandExecuter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class CommandConsumerImpl implements CommandConsumer {

    private final CommandExecuter commandExecuter;
    private final Map<CommandEnum, ExceptionHandler> exceptionHandlers;

    private BlockingQueue<Command> queue;

    CommandConsumerImpl(CommandExecuter commandExecuter, BlockingQueue<Command> queue) throws InterruptedException {
        this.commandExecuter = commandExecuter;
        exceptionHandlers = new HashMap<>();
        exceptionHandlers.put(CommandEnum.FIRE, new FireExceptionHandler(queue));
        exceptionHandlers.put(CommandEnum.MOVE, new MoveExceptionHandler(queue));
        exceptionHandlers.put(CommandEnum.TURN, new TurnExceptionHandler(queue));
        exceptionHandlers.put(CommandEnum.STOP, new StopExceptionHandler(queue));
        readFromQ(queue);
    }


    @Override
    public void readFromQ(BlockingQueue<Command> queue) throws InterruptedException {
        do {
            Command command = queue.poll();
            try {
                commandExecuter.executeCommand(command);
            } catch (Exception e) {
                exceptionHandlers.getOrDefault(command.getAction(), new DefaultExceptionHandler()).handle(command, e);
            }
        } while (!queue.isEmpty());
    }
}
