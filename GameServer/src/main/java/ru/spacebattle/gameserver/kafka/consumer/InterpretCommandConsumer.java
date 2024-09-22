package ru.spacebattle.gameserver.kafka.consumer;

import org.springframework.kafka.support.Acknowledgment;
import ru.spacebattle.dto.InterpretCommandRequestDto;

public interface InterpretCommandConsumer {

    void consume(InterpretCommandRequestDto interpretCommandRequestDto, Acknowledgment acknowledgment);
}
