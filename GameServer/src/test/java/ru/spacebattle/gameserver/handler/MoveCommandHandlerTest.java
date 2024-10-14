package ru.spacebattle.gameserver.handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.spacebattle.entities.Command;
import ru.spacebattle.entities.UObject;
import ru.spacebattle.enums.CommandEnum;

@ExtendWith(MockitoExtension.class)
public class MoveCommandHandlerTest {

    @InjectMocks
    private MoveCommandHandler moveCommandHandler;

    @Test
    void success_move_object() {
        UObject uObject = new UObject();
        Assertions.assertDoesNotThrow(() -> {
            moveCommandHandler.handle(new Command(CommandEnum.MOVE, uObject, 1, -1));
        });
    }
}
