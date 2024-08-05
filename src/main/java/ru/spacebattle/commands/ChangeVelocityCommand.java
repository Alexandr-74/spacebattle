package ru.spacebattle.commands;

import ru.spacebattle.entities.UObject;
import ru.spacebattle.enums.UObjectProperties;

public class ChangeVelocityCommand implements ChangeVelocity {

    private final UObject uObject;

    ChangeVelocityCommand(UObject uObject) {
        this.uObject = uObject;
    }

    @Override
    public void changeVelocity(int velocityDelta) {
        int currentVelocity = (int) uObject.getProperties().getOrDefault(UObjectProperties.VELOCITY, 0);
        uObject.setProperty(UObjectProperties.VELOCITY, currentVelocity != 0 ? currentVelocity + velocityDelta : 0);
    }
}
