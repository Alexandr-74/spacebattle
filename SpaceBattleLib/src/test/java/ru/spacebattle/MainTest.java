package ru.spacebattle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.spacebattle.commands.BurnFuelCommand;
import ru.spacebattle.commands.CheckFuelCommand;
import ru.spacebattle.commands.MovementCommand;
import ru.spacebattle.commands.TurnCommand;
import ru.spacebattle.entities.UObject;
import ru.spacebattle.enums.UObjectProperties;
import ru.spacebattle.exception.BurnFuelException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MainTest {

    public UObject uObject;
    private MovementCommand movementCommand;
    private TurnCommand turnCommand;
    private CheckFuelCommand checkFuelCommand;
    private BurnFuelCommand burnFuelCommand;

    @BeforeEach
    void set_up() {
        uObject = new UObject();
        uObject.setProperty(UObjectProperties.FUEL_VOLUME, 10);
        checkFuelCommand = new CheckFuelCommand(uObject);
        burnFuelCommand = new BurnFuelCommand(uObject);
        movementCommand = new MovementCommand(uObject);
        turnCommand = new TurnCommand(uObject);

    }

    @Test
    @DisplayName("Объект правильно двигается в разных направлениях")
    void test_movement_success() throws Exception {
        assertEquals(movementCommand.getPosition().x, 0);
        assertEquals(movementCommand.getPosition().y, 0);
        checkFuelCommand.check();
        turnCommand.turn(1);
        movementCommand.move(6);
        burnFuelCommand.burnFuel(4);
        assertEquals(movementCommand.getPosition().x, 6);
        assertEquals(movementCommand.getPosition().y, 6);
        checkFuelCommand.check();
        turnCommand.turn(6);
        movementCommand.move(7);
        burnFuelCommand.burnFuel(4);
        assertEquals(movementCommand.getPosition().x, 6);
        assertEquals(movementCommand.getPosition().y, -1);
    }


    @Test
    @DisplayName("Ошибка положения объекта")
    void test_movement_position_error() {
        movementCommand.position = null;
        assertThrows(Exception.class, () -> movementCommand.move(6), "Ошибка чтения позиции объекта");
    }

    @Test
    @DisplayName("Ошибка скорости объекта")
    void test_movement_valocity_error() {
        movementCommand.position = null;
        assertThrows(Exception.class, () -> movementCommand.move(-3), "Ошибка получения скорости объекта, скорость должна быть выше 0");
    }


    @Test
    @DisplayName("Ошибка поворота объекта")
    void test_movement_turning_error() {
        movementCommand.position = null;
        assertThrows(Exception.class, () -> movementCommand.move(6), "Ошибка получения скорости объекта, скорость должна быть выше 0");
        assertThrows(Exception.class, () -> turnCommand.turn(-1), "Ошибка поворота объекта, направление должно больше или равно 0 и меньше %s");
    }

    @Test
    @DisplayName("Ошибка недостатка топлива")
    void test_movement_fuel_error() {
        uObject.getProperties().remove(UObjectProperties.FUEL_VOLUME);
        assertThrows(Exception.class, () -> checkFuelCommand.check(), "Не установлен параметр FuelVolume");
        try {
            uObject.setProperty(UObjectProperties.FUEL_VOLUME, 10);
            burnFuelCommand.burnFuel(10);
        } catch (BurnFuelException e) {
            throw new RuntimeException(e);
        }
        assertThrows(Exception.class, () -> checkFuelCommand.check(), "Для совершения действия не достаточно топлива");
    }
}
