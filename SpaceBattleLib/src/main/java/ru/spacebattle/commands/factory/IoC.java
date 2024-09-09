package ru.spacebattle.commands.factory;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class IoC {

    private static final Map<String, Map<String, Function<Object[], Object>>> scopes = new HashMap<>();


    public static <T> T resolve(String dependency, Function<Object[], Object> constructor, Object... params) throws Exception {
        if ("IoC.Register".equals(dependency)) {
            return (T) new RegisterDependencyCommand((String) params[0], constructor);
        } else {
            throw new Exception("Unknown dependency");
        }
    }

    public static <T> T resolve(String dependency, Object... params) {
        if (dependency != null && dependency.matches("IoC\\.Scope\\..*")) {
            if (!scopes.containsKey(dependency)) {
                scopes.put(dependency, new HashMap<>());
            }
            if (params.length == 0) {
                return (T) new ResolveDependencyStrategyCommand(scopes.get(dependency)).getScope();
            } else {
                return (T) new ResolveDependencyStrategyCommand(scopes.get(dependency)).resolve(String.valueOf(params[0]), Arrays.copyOfRange(params, 1, params.length));
            }
        } else if ("Adapter".equals(dependency)) {
            return (T) new GenerateAdapterStrategyCommand(scopes.get("IoC.Scope.Current")).resolve(dependency, params);
        } else {
            return (T) new ResolveDependencyStrategyCommand(scopes.get("IoC.Scope.Current")).resolve(dependency, params);
        }
    }
}
