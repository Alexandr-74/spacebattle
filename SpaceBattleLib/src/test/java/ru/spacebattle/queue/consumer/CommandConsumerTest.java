package ru.spacebattle.queue.consumer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.spacebattle.entities.Command;
import ru.spacebattle.enums.CommandEnum;
import ru.spacebattle.queue.executer.CommandExecuterImpl;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class CommandConsumerTest {

    @Test
    @DisplayName("Исключение команды FIRE")
    void fireCommandSuccess() throws InterruptedException {
        BlockingQueue<Command> testQueue = new ArrayBlockingQueue<>(1);
        testQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.FIRE));
        Assertions.assertDoesNotThrow(() -> new CommandConsumerImpl(new CommandExecuterImpl(), testQueue));
    }

    @Test
    @DisplayName("Исключение команды MOVE")
    void MoveCommandSuccess() throws InterruptedException {
        BlockingQueue<Command> testQueue = new ArrayBlockingQueue<>(1);
        testQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.MOVE));
        Assertions.assertDoesNotThrow(() -> new CommandConsumerImpl(new CommandExecuterImpl(), testQueue));
    }

    @Test
    @DisplayName("Исключение команды STOP")
    void StopCommandSuccess() throws InterruptedException {
        BlockingQueue<Command> testQueue = new ArrayBlockingQueue<>(1);
        testQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.STOP));
        Assertions.assertDoesNotThrow(() -> new CommandConsumerImpl(new CommandExecuterImpl(), testQueue));
    }

    @Test
    @DisplayName("Исключение команды TURN")
    void TurnCommandSuccess() throws InterruptedException {
        BlockingQueue<Command> testQueue = new ArrayBlockingQueue<>(1);
        testQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.TURN));
        Assertions.assertDoesNotThrow(() -> new CommandConsumerImpl(new CommandExecuterImpl(), testQueue));
    }


    @Test
    @DisplayName("Исключения нескольких команд")
    void SeveralCommandSuccess() throws InterruptedException {
        BlockingQueue<Command> testQueue = new ArrayBlockingQueue<>(5);
        testQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.TURN));
        testQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.FIRE));
        testQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.MOVE));
        testQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.UNDEFINED));
        testQueue.put(new Command(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), CommandEnum.STOP));
        Assertions.assertDoesNotThrow(() -> new CommandConsumerImpl(new CommandExecuterImpl(), testQueue));
    }
}
