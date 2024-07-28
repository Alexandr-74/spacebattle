package ru.spacebattle.entities;

import java.util.HashMap;
import java.util.Map;

public class UObject {
    private final Map<String, Object> properties = new HashMap<>();

    public HashMap<String, Object> getProperties() {
        return new HashMap<>(properties);
    }

    public void setProperty(String key, Object value) {
        properties.put(key, value);
    }
}
