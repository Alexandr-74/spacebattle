package ru.spacebattle.gameserver.service.chain;

public interface ICorExec<T> {
    Object exec(T context);
}
