package ru.spacebattle.gameserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import ru.spacebattle.dto.InterpretCommandRequestDto;

@Configuration
public class ConsumerKafkaConfig {

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, InterpretCommandRequestDto> kafkaListenerContainerFactory(ConsumerFactory<String, InterpretCommandRequestDto> consumerFactory) {
        var containerFactory = new ConcurrentKafkaListenerContainerFactory<String, InterpretCommandRequestDto>();
        containerFactory.setConsumerFactory(consumerFactory);
        containerFactory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        return containerFactory;
    }
}
