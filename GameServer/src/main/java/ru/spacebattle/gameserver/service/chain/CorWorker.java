package ru.spacebattle.gameserver.service.chain;

import java.util.function.*;

public class CorWorker<T, R> extends ICorWorker<T, R> {

    private Predicate<T> blockOn = any -> true;
    private final Function<T, R> blockHandle;
    private BiConsumer<T, Throwable> blockExcept = (context, throwable) -> { throw new RuntimeException(throwable.getMessage(), throwable); };

    public CorWorker(String title, Function<T, R> blockHandle) {
        this.title = title;
        this.blockHandle = blockHandle;
    }

    public CorWorker(String title, Predicate<T> blockOn, Function<T, R> blockHandle) {
        this.title = title;
        this.blockOn = blockOn;
        this.blockHandle = blockHandle;
    }

    public CorWorker(String title, Function<T, R> blockHandle, BiConsumer<T, Throwable> blockExcept) {
        this.title = title;
        this.blockHandle = blockHandle;
        this.blockExcept = blockExcept;
    }

    @Override
    Boolean on(T context) {
        return blockOn.test(context);
    }

    @Override
    R handle(T context) {
        return blockHandle.apply(context);
    }

    @Override
    void except(T context, Throwable throwable) {
        blockExcept.accept(context, throwable);
    }
}
