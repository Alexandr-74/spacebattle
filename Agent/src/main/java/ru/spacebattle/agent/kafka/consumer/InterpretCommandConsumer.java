package ru.spacebattle.agent.kafka.consumer;

import ru.spacebattle.dto.InterpretCommandResponseDto;

public interface InterpretCommandConsumer {

    void consume(InterpretCommandResponseDto interpretCommandResponseDto);
}
