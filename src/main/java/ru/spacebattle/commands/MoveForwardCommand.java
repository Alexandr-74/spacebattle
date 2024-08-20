package ru.spacebattle.commands;

import ru.spacebattle.entities.UObject;
import ru.spacebattle.measures.Vector;

import static ru.spacebattle.enums.UObjectProperties.BURN_FUEL_VELOCITY;
import static ru.spacebattle.enums.UObjectProperties.VELOCITY;

public class MoveForwardCommand implements Movable {

    private final UObject uObject;
    private final MovementCommand movementCommand;
    private final CheckFuelCommand checkFuelCommand;
    private final BurnFuelCommand burnFuelCommand;
    private Vector position;
    private Integer velocity;

    MoveForwardCommand(UObject uObject) {
        this.uObject = uObject;
        movementCommand = new MovementCommand(uObject);
        checkFuelCommand = new CheckFuelCommand(uObject);
        burnFuelCommand = new BurnFuelCommand(uObject);
        position = new Vector(0, 0);
        velocity = 0;
    }

    @Override
    public Vector move(int velocity) throws Exception {
        checkFuelCommand.check();
        Vector vector = movementCommand.move(velocity);

        int burnFuelVelocity = (int) uObject.getProperties().get(BURN_FUEL_VELOCITY);
        this.velocity = (int) uObject.getProperties().get(VELOCITY);
        burnFuelCommand.burnFuel(burnFuelVelocity);
        position.x = vector.x;
        position.y = vector.y;

        return position;
    }

    public Vector getPosition() {
        return position;
    }

    @Override
    public Integer getVelocity() {
        return velocity;
    }
}
