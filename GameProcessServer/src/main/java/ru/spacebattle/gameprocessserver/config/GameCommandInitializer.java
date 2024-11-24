package ru.spacebattle.gameprocessserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.spacebattle.commands.factory.IoC;
import ru.spacebattle.commands.factory.RegisterDependencyCommand;
import ru.spacebattle.commands.handler.ParallelCommandHandler;
import ru.spacebattle.entities.UObject;
import ru.spacebattle.enums.CommandEnum;
import ru.spacebattle.gameprocessserver.service.handler.CommandsHandler;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GameCommandInitializer {

    private final Map<CommandEnum, CommandsHandler> commandsHandlers;

    @EventListener(ApplicationReadyEvent.class)
    public void initGameCommands() throws Exception {
        IoC.<RegisterDependencyCommand>resolve("IoC.Register",
                        (Object[] args) -> {
                            UObject uObject = (UObject) args[0];
                            Object[] params = Arrays.copyOfRange(args, 1, args.length);
                            commandsHandlers.get(CommandEnum.MOVE).execute(uObject, params);
                            System.out.printf("%s: UObject %s command MOVE %s%n", Thread.currentThread(), args[0], args.length != 1 ? args[1] : null);
                            return args[0];
                        },
                        "MOVE")
                .execute();

        IoC.<RegisterDependencyCommand>resolve("IoC.Register",
                        (Object[] args) -> {
                            UObject uObject = (UObject) args[0];
                            commandsHandlers.get(CommandEnum.LOG).execute(uObject);
                            System.out.printf("%s: UObject %s command LOG %s%n", Thread.currentThread(), args[0], args.length != 1 ? args[1] : null);
                            return args[0];
                        },
                        "LOG")
                .execute();

        IoC.<RegisterDependencyCommand>resolve("IoC.Register",
                        (Object[] args) -> {
                            UObject uObject = (UObject) args[0];
                            Object[] params = Arrays.copyOfRange(args, 1, args.length);
                            commandsHandlers.get(CommandEnum.TURN).execute(uObject, params);
                            System.out.printf("%s: UObject %s command TURN %s%n", Thread.currentThread(), args[0], args.length != 1 ? args[1] : null);
                            return args[0];
                        },
                        "TURN")
                .execute();


        IoC.<RegisterDependencyCommand>resolve("IoC.Register",
                        (Object[] args) -> {
                            UObject uObject = (UObject) args[0];
                            commandsHandlers.get(CommandEnum.FIRE).execute(uObject);
                            System.out.printf("%s: UObject %s command FIRE %s%n", Thread.currentThread(), args[0], args.length != 1 ? args[1] : null);
                            return args[0];
                        },
                        "FIRE")
                .execute();

        IoC.<RegisterDependencyCommand>resolve("IoC.Register",
                        (Object[] args) -> {
                            ((ParallelCommandHandler) args[0]).setExecuting((any) -> false);
                            return System.out.printf("%s: UObject %s command HARD_STOP %s%n", Thread.currentThread(), args[0], args[1]);
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
    }
}
