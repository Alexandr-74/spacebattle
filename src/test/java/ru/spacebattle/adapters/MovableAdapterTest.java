package ru.spacebattle.adapters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.spacebattle.commands.Movable;
import ru.spacebattle.commands.factory.IoC;
import ru.spacebattle.commands.factory.RegisterDependencyCommand;
import ru.spacebattle.entities.UObject;
import ru.spacebattle.measures.Vector;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.spacebattle.enums.UObjectProperties.*;

public class MovableAdapterTest {

    @Test
    void create_adapter_test() {

        Assertions.assertDoesNotThrow(() -> {
            IoC.<RegisterDependencyCommand>resolve("IoC.Register",
                            (Object[] args) -> ((UObject) args[0]).getProperties().get(POSITION),
                            "Spaceship.Operations.Movable:position.get")
                    .execute();

            IoC.<RegisterDependencyCommand>resolve("IoC.Register",
                            (Object[] args) -> ((UObject) args[0]).getProperties().get(VELOCITY),
                            "Spaceship.Operations.Movable:velocity.get")
                    .execute();


            IoC.<RegisterDependencyCommand>resolve(
                    "IoC.Register",
                    (Object[] args) -> {

                        UObject movableObject = ((UObject) args[0]);
                        Vector position = (Vector) movableObject.getProperties().get(POSITION);
                        Integer velocity = (Integer) args[1];

                        if (position == null) {
                            throw new RuntimeException("Ошибка чтения позиции объекта");
                        }

                        if (velocity < 0) {
                            throw new RuntimeException("Ошибка получения скорости объекта, скорость должна быть выше 0");
                        }

                        Vector directionVector = (Vector) ofNullable(movableObject.getProperties().get(DIRECTION))
                                .orElseThrow(() -> new RuntimeException("Отсутствует направление движения"));
                        movableObject.setProperty(VELOCITY, velocity);

                        Vector currentPosition = new Vector(
                                velocity * directionVector.x,
                                velocity * directionVector.y);


                        position.x = position.x + currentPosition.x;
                        position.y = position.y + currentPosition.y;
                        return new Vector(position.x, position.y);
                    },
                    "Spaceship.Operations.Movable:move"
            ).execute();


            UObject uObject = new UObject();
            uObject.setProperty(POSITION, new Vector(0, 0));
            uObject.setProperty(DIRECTION, new Vector(1, 0));

            var adapter = IoC.<Movable>resolve("Adapter", Movable.class, uObject);
            assertEquals(adapter.getPosition().x, 0);
            assertEquals(adapter.getPosition().y, 0);
            assertNull(adapter.getVelocity());
            Vector move = adapter.move(1);
            assertEquals(move.x, 1);
            assertEquals(move.y, 0);
            assertEquals(adapter.getVelocity(), 1);
        });

    }
}
