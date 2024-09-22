package ru.spacebattle.commands;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.spacebattle.commands.factory.IoC;
import ru.spacebattle.commands.factory.RegisterDependencyCommand;
import ru.spacebattle.entities.UObject;
import ru.spacebattle.enums.UObjectProperties;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.spacebattle.enums.UObjectProperties.FUEL_VOLUME;

public class IoCTest {

    @Test
    void check_factory() {
        UObject uObject = new UObject();
        uObject.setProperty(FUEL_VOLUME, 10);
        assertDoesNotThrow(() -> {
            IoC.<RegisterDependencyCommand>resolve(
                    "IoC.Register",
                    (Object[] args) -> new CheckFuelCommand((UObject) args[0]),
                    "Commands.CheckFuel")
                    .execute("IoC.Scope.Current");

            CheckFuel checkFuel = IoC.<CheckFuelCommand>resolve("Commands.CheckFuel", uObject);

            checkFuel.check();
        });
    }


    @Test
    @DisplayName("Объект правильно двигается в разных направлениях")
    void test_movement_success() {
        UObject uObject = new UObject();
        uObject.setProperty(UObjectProperties.FUEL_VOLUME, 10);
        assertDoesNotThrow(() -> {


            IoC.<RegisterDependencyCommand>resolve(
                            "IoC.Register",
                            (Object[] args) -> new MovementCommand((UObject) args[0]),
                            "Commands.MoveCommand")
                    .execute("IoC.Scope.Current");
            MovementCommand movementCommand = IoC.resolve("Commands.MoveCommand", uObject);

            IoC.<RegisterDependencyCommand>resolve(
                            "IoC.Register",
                            (Object[] args) -> new CheckFuelCommand((UObject) args[0]),
                            "Commands.CheckFuel")
                    .execute("IoC.Scope.Current");
            CheckFuelCommand checkFuelCommand = IoC.resolve("Commands.CheckFuel", uObject);

            IoC.<RegisterDependencyCommand>resolve(
                            "IoC.Register",
                            (Object[] args) -> new TurnCommand((UObject) args[0]),
                            "Commands.Turn")
                    .execute("IoC.Scope.Current");
            TurnCommand turnCommand = IoC.resolve("Commands.Turn", uObject);


            IoC.<RegisterDependencyCommand>resolve(
                            "IoC.Register",
                            (Object[] args) -> new BurnFuelCommand((UObject) args[0]),
                            "Commands.BurnFuel")
                    .execute("IoC.Scope.Current");
            BurnFuelCommand burnFuelCommand = IoC.resolve("Commands.BurnFuel", uObject);


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
        });
    }

    @Test
    void check_factory_differentScopes() {
        UObject uObject = new UObject();
        uObject.setProperty(FUEL_VOLUME, 10);

        assertDoesNotThrow(() -> {
            IoC.<RegisterDependencyCommand>resolve(
                            "IoC.Register",
                            (Object[] args) -> 2,
                            "a")
                    .execute("IoC.Scope.Current");

            IoC.<RegisterDependencyCommand>resolve(
                            "IoC.Register",
                            (Object[] args) -> 3,
                            "a")
                    .execute("IoC.Scope.New");

            Integer aNew = IoC.<Integer>resolve("IoC.Scope.New","a");
            Integer a = IoC.<Integer>resolve("a");

           assertEquals(aNew, 3);
           assertEquals(a, 2);
        });
    }
}
