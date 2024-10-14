package ru.spacebattle.gameserver.service.chain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ICorWorker<T, R> implements ICorExec<T> {

    protected String title;
    protected String description;

    abstract Boolean on(T context);
    abstract R handle(T context);
    abstract void except(T context, Throwable throwable);

    public R exec(T context) {
        System.out.println(title);
        if (on(context)) {
            try {
               return handle(context);
            } catch (Throwable e) {
                except(context, e);
            }
        }

        return null;
    }

}
