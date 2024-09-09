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

    private final Map<Long, ParallelCommandHandler> gamesMap = new HashMap<>();
    private final Map<Long, UObject> uObjectsMap = new HashMap<>();

    private final BaseProducer baseProducer;

    @Override
    public void interpretCommand(InterpretCommandRequestDto interpretCommandRequestDto) {

        ParallelCommandHandler gameScope;
        try {
            gameScope = gamesMap.getOrDefault(interpretCommandRequestDto.getPlayId(), initGame(interpretCommandRequestDto.getPlayId()));
        } catch (Exception ex) {
            throw new DefaultException(String.format("Ошибка поиска игры по id = %s", interpretCommandRequestDto.getPlayId()), 404);
        }

        gameScope.getQueue().addAll(mapToICommand(interpretCommandRequestDto));
    }

    private void publish(ParallelCommandHandler parallelCommandHandler) {
        Executors.newCachedThreadPool().execute(() -> {
            while (parallelCommandHandler.getExecuting()) {
                if (!parallelCommandHandler.getDoneCommands().isEmpty()) {
                    Command doneCommand = parallelCommandHandler.getDoneCommands().poll();


                    log.info("публикуется выполненная команда " + doneCommand.getCommandEnum());
                    log.info("Параметры " + Arrays.toString(doneCommand.getParams()));
                    InterpretCommandResponseDto res;
                    if (doneCommand.isDone()) {
                        res = new InterpretCommandResponseDto(String.format("Выполнена команда %s с параметрами %s", doneCommand.getCommandEnum(), doneCommand.getStringParams()));
                    } else {
                        res = new InterpretCommandResponseDto(String.format("Команда %s с параметрами %s не выполнена из за ошибки %s", doneCommand.getCommandEnum(), doneCommand.getStringParams(), doneCommand.getMessage()));
                    }

                    baseProducer.sendCommand(UUID.randomUUID(), res);
                }
            }
        });

    }

    private List<Command> mapToICommand(InterpretCommandRequestDto interpretCommandRequestDto) {

        return interpretCommandRequestDto.getCommandsList().stream().map(interpretCommandDto -> {
                    CommandEnum commandEnum;
                    UObject uObject;
                    try {
                        commandEnum = CommandEnum.valueOf(interpretCommandDto.getCommandName());
                    } catch (IllegalArgumentException ex) {
                        throw new DefaultException(String.format("Получена неизвестная команда %s", interpretCommandDto.getCommandName()), 500);
                    }
                    try {
                        uObject = uObjectsMap.get(interpretCommandRequestDto.getUObjectId());
                    } catch (IllegalArgumentException ex) {
                        throw new DefaultException(String.format("Не найден объект выполнения команды %s", interpretCommandRequestDto.getUObjectId()), 404);
                    }

                    List<Object> params = new ArrayList<>();
                    params.add(uObject);
                    params.addAll(interpretCommandDto.getArgs());

                    return new Command(commandEnum, params.toArray());
                })
                .toList();
    }

    private ParallelCommandHandler initGame(long playId) {
        BlockingQueue<Command> queue = new LinkedBlockingQueue<>();

        ParallelCommandHandler parallelCommandHandler = new ParallelCommandHandler(queue);
        parallelCommandHandler.execute();
        gamesMap.put(playId, parallelCommandHandler);
        publish(parallelCommandHandler);

        return parallelCommandHandler;
    }
}
