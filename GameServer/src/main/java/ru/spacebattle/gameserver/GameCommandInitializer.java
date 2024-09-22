package ru.spacebattle.gameserver;

import ru.spacebattle.commands.factory.IoC;
import ru.spacebattle.commands.factory.RegisterDependencyCommand;
import ru.spacebattle.commands.handler.ParallelCommandHandler;

public class GameCommandInitializer {

    public static void initGameCommands() throws Exception {
        IoC.<RegisterDependencyCommand>resolve("IoC.Register",
                        (Object[] args) ->
                                System.out.printf("%s: UObject %s command MOVE %s%n", Thread.currentThread(), args[0], args.length != 1 ? args[1] : null),
                        "MOVE")
                .execute();

        IoC.<RegisterDependencyCommand>resolve("IoC.Register",
                        (Object[] args) ->
                                System.out.printf("%s: UObject %s command TURN %s%n", Thread.currentThread(), args[0], args.length != 1 ? args[1] : "no args"),
                        "TURN")
                .execute();

        IoC.<RegisterDependencyCommand>resolve("IoC.Register",
                        (Object[] args) ->
                                System.out.printf("%s: UObject %s command FIRE %s%n", Thread.currentThread(), args[0], args.length != 1 ? args[1] : "no args"),
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
