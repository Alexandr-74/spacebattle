package ru.spacebattle.entities;

import lombok.Data;
import ru.spacebattle.enums.UObjectProperties;

import java.util.HashMap;

@Data
public class UObject {
    private final HashMap<UObjectProperties, Object> properties = new HashMap<>();

    public void setProperty(UObjectProperties key, Object value) {
        properties.put(key, value);
    }
}
