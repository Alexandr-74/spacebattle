package ru.spacebattle.gameserver.handler;

import org.springframework.stereotype.Component;
import ru.spacebattle.commands.factory.IoC;
import ru.spacebattle.commands.factory.RegisterDependencyCommand;
import ru.spacebattle.entities.Command;
import ru.spacebattle.entities.UObject;
import ru.spacebattle.enums.CommandEnum;
import ru.spacebattle.gameserver.entity.CommandResponse;
import ru.spacebattle.gameserver.service.chain.CorWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class MoveCommandHandler implements CommandHandler {


    @Override
    public Command handle(Command command) {
        try {
            IoC.<RegisterDependencyCommand>resolve("IoC.Register",
                            this::moveHandle,
                            "MOVE")
                    .execute();
            IoC.resolve(command.getAction().name(), command.getParams());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private Function<UObject, Command> moveHandle(Object[] args) {
        CorWorker<UObject, List<List<UObject>>> defineLocalityWorker = new CorWorker<>(
                "Определяем окрестность",
                this::defineLocality
        );

        List<List<UObject>> oldLocality = defineLocalityWorker.exec((UObject) args[0]);

        CorWorker<UObject, List<List<UObject>>> changeLocalityWorker = new CorWorker<>(
                "Меняем окрестность",
                movableObject -> checkIsObjectMoveToNewLocality(movableObject, oldLocality, new Object[]{args[1], args[2]}),
                movableObject -> moveLocality(movableObject, oldLocality, new Object[]{args[1], args[2]})
        );

        List<List<UObject>> newLocality = changeLocalityWorker.exec((UObject) args[0]);

        CorWorker<UObject, Command> checkIsCollisionWorker = new CorWorker<>(
                "Определяем пересекающиеся объекты",
                movableObject -> checkIsCollision(newLocality, movableObject),
                (movableObject, throwable) -> doOnCollision(movableObject, new RuntimeException(String.format("Обнаружена коллизия у объекта с параметрами %s", movableObject)))
        );

        checkIsCollisionWorker.exec((UObject) args[0]);

        return checkIsCollisionWorker::exec;
    }

    private Void doOnCollision(UObject movableObject, Throwable throwable) {
        throw new RuntimeException(String.format("Обнаружена коллизия у объекта с параметрами %s", movableObject));
    }

    private Boolean checkIsObjectMoveToNewLocality(UObject movableObject, List<List<UObject>> newLocality, Object[] objects) {
        System.out.println("Проверяем попадает ли объект в новую окрестность");
        return true;
    }

    private List<List<UObject>> defineLocality(UObject uObject) {
        System.out.println("Определяем окрестность в которой находится объект");
        return new ArrayList<>();
    }

    private List<List<UObject>> moveLocality(UObject uObject, List<List<UObject>> newLocality, Object[] params) {
        System.out.printf("Выполняем перемещение с параметрами [%d, %d]", params[0], params[1]);
        return new ArrayList<>();
    }

    private Command checkIsCollision(List<List<UObject>> newLocality, UObject uObject) {
        System.out.print("Проверяем пересекаются ли элементы окрестности с новым элементом");
        return new CommandResponse(CommandEnum.MOVE);
    }

    @Override
    public CommandEnum getCommandEnum() {
        return CommandEnum.MOVE;
    }
}
