package ru.spacebattle.queue.executer;

import ru.spacebattle.entities.Command;
import ru.spacebattle.exception.DefaultException;

public interface CommandExecuter {

    void executeCommand(Command cmd) throws DefaultException;
}
