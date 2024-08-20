package ru.spacebattle.commands.factory;

import java.lang.reflect.Proxy;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class GenerateAdapterStrategyCommand {

    private final Map<String, Function<Object[], Object>> dependencies;

    public GenerateAdapterStrategyCommand(Map<String, Function<Object[], Object>> scope) {
        dependencies = scope;
    }

    public Object resolve(String dependency, Object[] args) {

        Class<?> interfaze = (Class<?>) args[0];

        return Proxy.newProxyInstance(
                interfaze.getClassLoader(),
                new Class[]{interfaze},
                (proxy, method, args1) -> {
                    String methodName = "Spaceship.Operations." +
                            interfaze.getSimpleName() +
                            ":";
                    if (method.getName().matches("([get]|[set]).*")) {
                        methodName = methodName +
                                method.getName().substring(3).toLowerCase() +
                                "." +
                                method.getName().substring(0, 3);
                    } else {
                        methodName = methodName +
                                method.getName();
                    }

                    if (Objects.isNull(args1)) {
                        args1 = new Object[1];
                    }

                    List<Object> params = new ArrayList<>();
                    Collections.addAll(params, args[1]);
                    Collections.addAll(params, args1);

                    return IoC.resolve(methodName, params.toArray());
                });
    }

}
