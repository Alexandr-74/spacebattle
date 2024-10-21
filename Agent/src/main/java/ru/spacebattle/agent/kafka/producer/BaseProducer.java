package ru.spacebattle.agent.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.spacebattle.dto.InterpretCommandRequestDto;
import ru.spacebattle.entities.Command;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class BaseProducer {

    private final KafkaTemplate<String, Command> kafkaTemplate;

    @Value("${spring.kafka.topic}")
    private String topic;

    public void sendCommand(Command command) {
        log.info("Отправляю сообщение в очередь {}", command);
        kafkaTemplate.send(topic, command);
    }
}
