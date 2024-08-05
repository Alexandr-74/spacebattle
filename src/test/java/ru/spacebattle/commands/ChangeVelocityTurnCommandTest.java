package ru.spacebattle.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.spacebattle.entities.UObject;
import ru.spacebattle.enums.UObjectProperties;
import ru.spacebattle.exception.TurnException;

import static org.junit.jupiter.api.Assertions.*;

public class ChangeVelocityTurnCommandTest {
    public UObject uObject;
    private ChangeVelocityTurnCommand changeVelocityTurnCommand;

    @BeforeEach
    void set_up() {
        uObject = new UObject();
        uObject.setProperty(UObjectProperties.TURN_VELOCITY_DELTA, -1);
        uObject.setProperty(UObjectProperties.VELOCITY, 5);
        changeVelocityTurnCommand = new ChangeVelocityTurnCommand(uObject);
    }

    @Test
    @DisplayName("Проверка поворота с изменением скорости")
    void moveForward_Success() {
        assertEquals(changeVelocityTurnCommand.getDirection().x, 1);
        assertEquals(changeVelocityTurnCommand.getDirection().y, 0);
        assertDoesNotThrow(() -> changeVelocityTurnCommand.turn(3));
        assertEquals(changeVelocityTurnCommand.getDirection().x, -1);
        assertEquals(changeVelocityTurnCommand.getDirection().y, 1);
        uObject.getProperties().remove(UObjectProperties.TURN_VELOCITY_DELTA);
        assertThrows(TurnException.class, () -> changeVelocityTurnCommand.turn(3), "Ошибка поворота: не указано изменение скорости");
    }
}
