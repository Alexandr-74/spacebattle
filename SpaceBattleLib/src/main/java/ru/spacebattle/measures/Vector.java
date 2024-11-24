package ru.spacebattle.measures;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class Vector {

    public int x;
    public int y;

    @JsonCreator
    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector(LinkedHashMap<Integer, Integer> hashMap) {
        this.x = hashMap.get("x");
        this.y = hashMap.get("y");
    }

    public Vector(Vector vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    public Vector plus(Vector vector) {
        this.x += vector.x;
        this.y += vector.y;
        return this;
    }

    @Override
    public String toString() {
        return "{x=" + x +", y=" + y + "}";
    }
}
