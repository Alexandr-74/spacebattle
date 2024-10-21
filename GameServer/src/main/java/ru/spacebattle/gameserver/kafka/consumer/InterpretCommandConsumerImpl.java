package ru.spacebattle.gameserver.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.spacebattle.entities.Command;
import ru.spacebattle.gameserver.service.InterpretCommandService;
import ru.spacebattle.dto.InterpretCommandRequestDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class InterpretCommandConsumerImpl implements InterpretCommandConsumer {

    private final InterpretCommandService interpretCommandService;

    @Override
    @KafkaListener(topics = "${spring.kafka.consumer.topic}")
    public void consume(@Payload Command command, Acknowledgment acknowledgment) {
        try {
            log.info(String.format("Принято сообщение от агента %s", command));
            interpretCommandService.interpretCommand(command);
        } catch (Exception ex) {
            log.error(String.format("Ошибка обработки команды %s", ex.getMessage()), ex);
        } finally {
            log.info("acknowledge");
            acknowledgment.acknowledge();
        }
    }
}
