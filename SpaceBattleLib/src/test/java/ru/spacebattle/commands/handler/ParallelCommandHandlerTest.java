package ru.spacebattle.commands.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.spacebattle.commands.factory.IoC;
import ru.spacebattle.commands.factory.RegisterDependencyCommand;
import ru.spacebattle.entities.Command;
import ru.spacebattle.enums.CommandEnum;
import ru.spacebattle.measures.Vector;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParallelCommandHandlerTest {

    @BeforeEach
    void setUp() throws Exception {
        IoC.<RegisterDependencyCommand>resolve("IoC.Register",
                        (Object[] args) ->
                                System.out.printf("%s command MOVE %s%n", Thread.currentThread(), args.length != 0 ? args[0] : null),
                        "MOVE")
                .execute();

        IoC.<RegisterDependencyCommand>resolve("IoC.Register",
                        (Object[] args) ->
                                System.out.printf("%s command TURN %s%n", Thread.currentThread(), args.length != 0 ? args[0] : "no args"),
                        "TURN")
                .execute();

        IoC.<RegisterDependencyCommand>resolve("IoC.Register",
                        (Object[] args) ->
                                System.out.printf("%s command FIRE %s%n", Thread.currentThread(), args.length != 0 ? args[0] : "no args"),
                        "FIRE")
                .execute();

        IoC.<RegisterDependencyCommand>resolve("IoC.Register",
                        (Object[] args) -> {
                            ((ParallelCommandHandler) args[0]).setExecuting((any) -> false);
                            return System.out.printf("%s command HARD_STOP %s%n", Thread.currentThread(), args[0]);
                        },
                        "HARD_STOP")
                .execute();


        IoC.<RegisterDependencyCommand>resolve("IoC.Register",
                        (Object[] args) -> {
                            ParallelCommandHandler commandHandler = ((ParallelCommandHandler) args[0]);
                            commandHandler.setExecuting((any) -> !commandHandler.getQueue().isEmpty());
                            return System.out.printf("%s command SOFT_STOP %s%n", Thread.currentThread(), args[0]);
                        },
                        "SOFT_STOP")
                .execute();

        IoC.<RegisterDependencyCommand>resolve("IoC.Register",
                        (Object[] args) ->
                                System.out.printf("%s command MOVE_TO %s%n", Thread.currentThread(), args.length != 0 ? args[0] : null),
                        "MOVE_TO")
                .execute();

    }

    @Test
    @DisplayName("Обработка очереди с мягкой остановкой, добавили в очередь 1006 объектов и ждем завершения")
    void check_execute_parallel_soft() throws Exception {
        BlockingQueue<Command> blockingQueue = new LinkedBlockingQueue<>();


        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.MOVE, 1));
        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.TURN, new Vector(0, 1)));
        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(),  CommandEnum.FIRE));
        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(),  CommandEnum.MOVE, -1));
        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(),  CommandEnum.FIRE));

        Thread thread = new Thread(() -> {
            int i = 0;
            while (i < 500) {
                try {
                    blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.MOVE, i++));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        ParallelCommandHandler commandHandler = new ParallelCommandHandler(blockingQueue);
        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.SOFT_STOP, commandHandler));
        thread.start();
        commandHandler.execute();

        commandHandler.getExecutor().shutdown();
        commandHandler.getExecutor().awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        assertEquals(commandHandler.getDoneCommands().size(), 506);
    }


    @Test
    @DisplayName("Обработка очереди с жесткой остановкой, добавили в очередь 1006 объектов, но после 6 происходит жесткая остановка")
    void check_execute_parallel_hard() throws Exception {
        BlockingQueue<Command> blockingQueue = new LinkedBlockingQueue<>();

        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.MOVE, 1));
        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(),CommandEnum.TURN, new Vector(0, 1)));
        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(),CommandEnum.FIRE));
        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.MOVE, -1));
        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.FIRE));

        Thread thread = new Thread(() -> {
            int i = 0;
            while (i < 1000) {
                try {
                    blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.MOVE, i++));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        ParallelCommandHandler commandHandler = new ParallelCommandHandler(blockingQueue);
        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.HARD_STOP, commandHandler));
        commandHandler.execute();
        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.MOVE, -5));
        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.FIRE));

        thread.start();
        commandHandler.getExecutor().shutdown();
        commandHandler.getExecutor().awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        assertEquals(commandHandler.getDoneCommands().size(), 6);
    }

    @Test
    @DisplayName("Проверка измнения состояния на moveTo")
    void check_execute_moveTo_state() throws Exception {
        BlockingQueue<Command> blockingQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Command> backupQueue = new LinkedBlockingQueue<>();

        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.MOVE, 1));
        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.TURN, new Vector(0, 1)));
        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.FIRE));
        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.MOVE, -1));


        ParallelCommandHandler commandHandler = new ParallelCommandHandler(blockingQueue);
        commandHandler.setBackUpQueueBlockingQueue(backupQueue);
        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.SOFT_STOP, commandHandler));
        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.MOVE_TO));
        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.FIRE));
        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.TURN, new Vector(1, 1)));
        commandHandler.execute();
        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.MOVE, -5));
        blockingQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.FIRE));

        commandHandler.getExecutor().shutdown();
        commandHandler.getExecutor().awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        assertEquals(commandHandler.getDoneCommands().size(), 6);
        assertEquals(backupQueue.size(), 4);
    }
}
