package ru.spacebattle.entities;

import ru.spacebattle.enums.UObjectProperties;

import java.util.HashMap;

public class UObject {
    private final HashMap<UObjectProperties, Object> properties = new HashMap<>();

    public HashMap<UObjectProperties, Object> getProperties() {
        return properties;
    }

    public void setProperty(UObjectProperties key, Object value) {
        properties.put(key, value);
    }
}
