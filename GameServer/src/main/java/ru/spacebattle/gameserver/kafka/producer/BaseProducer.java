package ru.spacebattle.gameserver.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.spacebattle.dto.InterpretCommandRequestDto;
import ru.spacebattle.dto.InterpretCommandResponseDto;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class BaseProducer {

    private final KafkaTemplate<String, InterpretCommandResponseDto> kafkaTemplate;

    @Value("${spring.kafka.producer.topic}")
    private String topic;

    public void sendCommand(UUID key, InterpretCommandResponseDto interpretCommandResponseDto) {
        log.info("Выполнена команда {}", interpretCommandResponseDto);
        kafkaTemplate.send(topic, key.toString(), interpretCommandResponseDto);
    }
}
