package ru.spacebattle.commands.factory;

import java.util.Map;
import java.util.function.Function;

public class RegisterDependencyCommand {

    private final String dependency;
    private final Function<Object[], Object> dependencyResolverStrategy;

    public RegisterDependencyCommand(String dependency, Function<Object[], Object> dependencyResolverStrategy) {
        this.dependency = dependency;
        this.dependencyResolverStrategy = dependencyResolverStrategy;
    }

    public void execute(String scope) {
        var currentScope = IoC.<Map<String, Function<Object[], Object>>>resolve(scope);
        currentScope.put(dependency, dependencyResolverStrategy);
    }
}
