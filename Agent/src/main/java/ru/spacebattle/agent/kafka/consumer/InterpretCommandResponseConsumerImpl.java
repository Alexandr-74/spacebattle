package ru.spacebattle.agent.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.spacebattle.agent.service.GameService;
import ru.spacebattle.dto.InterpretCommandResponseDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class InterpretCommandResponseConsumerImpl implements InterpretCommandConsumer {

    private final GameService gameService;

    @Override
    @KafkaListener(topics = "${spring.kafka.consumer.topic}")
    public void consume(@Payload InterpretCommandResponseDto interpretCommandResponseDto) {
        log.info("Принято сообщение от сервера {}", interpretCommandResponseDto);
        gameService.saveGameData(interpretCommandResponseDto);
    }
}
