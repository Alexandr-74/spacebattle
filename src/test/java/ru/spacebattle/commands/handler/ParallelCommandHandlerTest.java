package ru.spacebattle.commands.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.spacebattle.commands.factory.IoC;
import ru.spacebattle.commands.factory.RegisterDependencyCommand;
import ru.spacebattle.entities.Command;
import ru.spacebattle.enums.CommandEnum;
import ru.spacebattle.measures.Vector;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
                                System.out.printf("%s command TURN %s%n", Thread.currentThread(), args.length != 0 ? args[0] : null),
                        "TURN")
                .execute();

        IoC.<RegisterDependencyCommand>resolve("IoC.Register",
                        (Object[] args) ->
                                System.out.printf("%s command FIRE %s%n", Thread.currentThread(), args.length != 0 ? args[0] : null),
                        "FIRE")
                .execute();
    }

    @Test
    @DisplayName("Обработка очереди")
    void check_execute_parallel() throws Exception {
        BlockingQueue<Command> blockingQueue = new LinkedBlockingQueue<>();

        blockingQueue.put(new Command(CommandEnum.MOVE, 1));
        blockingQueue.put(new Command(CommandEnum.TURN, new Vector(0, 1)));
        blockingQueue.put(new Command(CommandEnum.FIRE));
        blockingQueue.put(new Command(CommandEnum.MOVE, -1));
        blockingQueue.put(new Command(CommandEnum.FIRE));

        ParallelCommandHandler commandHandler = new ParallelCommandHandler(blockingQueue);
        commandHandler.execute();


        commandHandler.getExecutor().submit(() -> {
            assertEquals(commandHandler.getDoneCommands().size(), 5);
        });
    }

    @Test
    @DisplayName("Обработка очереди с мягкой остановкой")
    void check_execute_parallel_soft() throws Exception {
        BlockingQueue<Command> blockingQueue = new LinkedBlockingQueue<>();

        blockingQueue.put(new Command(CommandEnum.SOFT_STOP));
        blockingQueue.put(new Command(CommandEnum.MOVE, 1));
        blockingQueue.put(new Command(CommandEnum.TURN, new Vector(0, 1)));
        blockingQueue.put(new Command(CommandEnum.FIRE));
        blockingQueue.put(new Command(CommandEnum.MOVE, -1));
        blockingQueue.put(new Command(CommandEnum.FIRE));

        Thread thread = new Thread(() -> {
            int i = 0;
            while (i < 1000) {
                try {
                    blockingQueue.put(new Command(CommandEnum.MOVE, i++));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();

        ParallelCommandHandler commandHandler = new ParallelCommandHandler(blockingQueue);
        commandHandler.execute();

        commandHandler.getExecutor().submit(() -> {
            assertNotEquals(commandHandler.getDoneCommands().size(), 1006);
        });


    }


    @Test
    @DisplayName("Обработка очереди с жесткой остановкой")
    void check_execute_parallel_hard() throws Exception {
        BlockingQueue<Command> blockingQueue = new LinkedBlockingQueue<>();

        blockingQueue.put(new Command(CommandEnum.MOVE, 1));
        blockingQueue.put(new Command(CommandEnum.TURN, new Vector(0, 1)));
        blockingQueue.put(new Command(CommandEnum.HARD_STOP));
        blockingQueue.put(new Command(CommandEnum.FIRE));
        blockingQueue.put(new Command(CommandEnum.MOVE, -1));
        blockingQueue.put(new Command(CommandEnum.FIRE));

        Thread thread = new Thread(() -> {
            int i = 0;
            while (i < 1000) {
                try {
                    blockingQueue.put(new Command(CommandEnum.MOVE, i++));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        ParallelCommandHandler commandHandler = new ParallelCommandHandler(blockingQueue);
        commandHandler.execute();


        thread.start();

        commandHandler.getExecutor().submit(() -> {
            assertEquals(commandHandler.getDoneCommands().size(), 3);
        });
    }
}
