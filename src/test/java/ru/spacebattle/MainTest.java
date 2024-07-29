package ru.spacebattle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.spacebattle.entities.UObject;
import ru.spacebattle.movement.Movement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MainTest {

    private Movement movement;

    @BeforeEach
    void set_up() {
        UObject uObject = new UObject();
        movement = new Movement(uObject);
    }

    @Test
    @DisplayName("Объект правильно двигается в разных направлениях")
    void test_movement_success() throws Exception {
        assertEquals(movement.getPosition().x, 0);
        assertEquals(movement.getPosition().y, 0);
        movement.move(6, 1);
        assertEquals(movement.getPosition().x, 6);
        assertEquals(movement.getPosition().y, 6);
        movement.move(7, 6);
        assertEquals(movement.getPosition().x, 6);
        assertEquals(movement.getPosition().y, -1);
    }


    @Test
    @DisplayName("Ошибка положения объекта")
    void test_movement_position_error() {
        movement.position = null;
        assertThrows(Exception.class, () -> movement.move(6, 1), "Ошибка чтения позиции объекта");
    }

    @Test
    @DisplayName("Ошибка скорости объекта")
    void test_movement_valocity_error() {
        movement.position = null;
        assertThrows(Exception.class, () -> movement.move(-3, 1), "Ошибка получения скорости объекта, скорость должна быть выше 0");
    }


    @Test
    @DisplayName("Ошибка поворота объекта")
    void test_movement_turning_error() {
        movement.position = null;
        assertThrows(Exception.class, () -> movement.move(6, 8 + 1), "Ошибка получения скорости объекта, скорость должна быть выше 0");
        assertThrows(Exception.class, () -> movement.move(6, -1), "Ошибка поворота объекта, направление должно больше или равно 0 и меньше %s");
    }
}
