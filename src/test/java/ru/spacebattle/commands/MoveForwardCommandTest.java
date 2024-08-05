package ru.spacebattle.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.spacebattle.entities.UObject;
import ru.spacebattle.enums.UObjectProperties;
import ru.spacebattle.exception.LowFuelException;
import ru.spacebattle.measures.Vector;

import static org.junit.jupiter.api.Assertions.*;

public class MoveForwardCommandTest {

    private MoveForwardCommand moveForwardCommand;

    public UObject uObject;

    @BeforeEach
    void set_up() {
        uObject = new UObject();
        uObject.setProperty(UObjectProperties.FUEL_VOLUME, 10);
        uObject.setProperty(UObjectProperties.BURN_FUEL_VELOCITY, 5);
        uObject.setProperty(UObjectProperties.DIRECTION, new Vector(1,0));
        moveForwardCommand = new MoveForwardCommand(uObject);
    }

    @Test
    @DisplayName("Проверка движения с сжиганием топлива по прямой")
    void moveForward_Success(){
        assertEquals(moveForwardCommand.getPosition().x, 0);
        assertEquals(moveForwardCommand.getPosition().y, 0);
        assertDoesNotThrow(() -> moveForwardCommand.move(5));
        assertEquals(moveForwardCommand.getPosition().x, 5);
        assertEquals(moveForwardCommand.getPosition().y, 0);
        assertDoesNotThrow(() -> moveForwardCommand.move(3));
        assertEquals(moveForwardCommand.getPosition().x, 8);
        assertEquals(moveForwardCommand.getPosition().y, 0);
        assertThrows(LowFuelException.class, () -> moveForwardCommand.move(3), "Для совершения действия не достаточно топлива");
    }
}
