package ru.spacebattle.gameserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.spacebattle.dto.InterpretCommandResponseDto;
import ru.spacebattle.gameserver.kafka.producer.BaseProducer;
import ru.spacebattle.commands.handler.ParallelCommandHandler;
import ru.spacebattle.dto.InterpretCommandRequestDto;
import ru.spacebattle.entities.Command;
import ru.spacebattle.entities.UObject;
import ru.spacebattle.enums.CommandEnum;
import ru.spacebattle.exception.DefaultException;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Component
@RequiredArgsConstructor
public class InterpretCommandServiceImpl implements InterpretCommandService {


    private final GameService gameService;
    private final BaseProducer baseProducer;

    @Override
    public void interpretCommand(Command command) {

        ParallelCommandHandler gameScope = gameService.getGame(command.getGameId());
        UObject uObject = gameService.getUObject(command.getGameId(), command.getuObjectId());

        command.setParams(new Object[]{
                uObject,
                command.getParams()});

        gameScope.getQueue().add(command);
    }

    private void publish(ParallelCommandHandler parallelCommandHandler) {
        Executors.newCachedThreadPool().execute(() -> {
            while (parallelCommandHandler.getExecuting()) {
                if (!parallelCommandHandler.getDoneCommands().isEmpty()) {
                    Command doneCommand = parallelCommandHandler.getDoneCommands().poll();

                    log.info("публикуется выполненная команда " + doneCommand.getAction());
                    log.info("Параметры " + Arrays.toString(doneCommand.getParams()));
                    InterpretCommandResponseDto res;
                    if (doneCommand.isDone()) {
                        res = new InterpretCommandResponseDto(String.format("Выполнена команда %s с параметрами %s", doneCommand.getAction(), doneCommand.getStringParams()));
                    } else {
                        res = new InterpretCommandResponseDto(String.format("Команда %s с параметрами %s не выполнена из за ошибки %s", doneCommand.getAction(), doneCommand.getStringParams(), doneCommand.getMessage()));
                    }

                    baseProducer.sendCommand(UUID.randomUUID(), res);
                }
            }
        });
    }
}
