package ru.spacebattle.gameserver.kafka.consumer;

import org.springframework.kafka.support.Acknowledgment;
import ru.spacebattle.dto.InterpretCommandRequestDto;
import ru.spacebattle.entities.Command;

public interface InterpretCommandConsumer {

    void consume(Command command, Acknowledgment acknowledgment);
}
