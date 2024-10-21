package ru.spacebattle.queue.executer;

import ru.spacebattle.entities.Command;
import ru.spacebattle.exception.*;

public class CommandExecuterImpl implements CommandExecuter {

    public CommandExecuterImpl() {
    }

    @Override
    public void executeCommand(Command cmd) throws DefaultException {

        switch (cmd.getAction()) {
            case FIRE -> throw new FireException("Not implemented yet", 501);
            case MOVE -> throw new MoveException("Not implemented yet", 501);
            case TURN -> throw new TurnException("Not implemented yet", 501);
            case STOP -> throw new StopException("Not implemented yet", 501);
            case LOG -> System.out.printf("Получена команда логирования %s%n", cmd.getAction());
            default -> throw new DefaultException("Undefined command", 500);
        }
    }
}
