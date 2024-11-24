package ru.spacebattle.gameprocessserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.spacebattle.commands.handler.ParallelCommandHandler;
import ru.spacebattle.entities.Command;
import ru.spacebattle.entities.UObject;
import ru.spacebattle.enums.UObjectProperties;

import java.util.*;
import java.util.concurrent.Executors;

import static java.util.Optional.ofNullable;

@Slf4j
@Component
@RequiredArgsConstructor
public class InterpretCommandServiceImpl implements InterpretCommandService {


    private final GameResourcesService gameResourcesService;

    @Override
    public void interpretCommand(Command command) {
        ParallelCommandHandler gameScope = gameResourcesService.getGameHandlerByGame(command.getGameId());
        UObject uObject = gameResourcesService.getUObjectByGameIdAndObjectId(command.getGameId(), command.getUObjectId());

        List<Object> params = new ArrayList<>();
        params.add(uObject);
        params.addAll(Arrays.stream(command.getParams()).toList());

        command.setParams(params.toArray());

        gameScope.getQueue().add(command);
        publish(gameScope);
    }

    private void publish(ParallelCommandHandler parallelCommandHandler) {
        Executors.newCachedThreadPool().execute(() -> {
            while (parallelCommandHandler.getExecuting()) {
                if (!parallelCommandHandler.getDoneCommands().isEmpty()) {
                    ofNullable(parallelCommandHandler.getDoneCommands().poll()).ifPresent(doneCommand -> {
                        if (checkIsSomeoneWins(parallelCommandHandler.getGameId())) {
                            gameResourcesService.removeGame(parallelCommandHandler.getGameId());
                            parallelCommandHandler.setExecuting((any) -> false);
                        }
                    });
                }
            }
        });
    }

    private boolean checkIsSomeoneWins(UUID gameId) {
        Set<String> winner = new HashSet<>();
        gameResourcesService.getGameResources(gameId).values().forEach(uObject -> winner.add(uObject.getProperties().get(UObjectProperties.PLAYER_NAME).toString()));
        return winner.size() == 1;
    }
}
