package ru.spacebattle.commands.factory;

import java.util.Map;
import java.util.function.Function;

public class ResolveDependencyStrategyCommand {

    private final Map<String, Function<Object[], Object>> dependencies;

    public ResolveDependencyStrategyCommand(Map<String, Function<Object[], Object>> scope) {
        dependencies = scope;
    }

    public Object resolve(String dependency, Object[] args) {

        if (dependencies.containsKey(dependency)) {
            return dependencies.get(dependency).apply(args);
        } else {
            return dependencies.get("IoC.Scope.Parent").apply(args);
        }
    }

    public Map<String, Function<Object[], Object>> getScope() {
        return dependencies;
    }
}
