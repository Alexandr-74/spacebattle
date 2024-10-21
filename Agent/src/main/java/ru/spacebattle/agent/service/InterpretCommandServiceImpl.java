package ru.spacebattle.agent.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.spacebattle.agent.kafka.producer.BaseProducer;
import ru.spacebattle.dto.InterpretCommandRequestDto;
import ru.spacebattle.entities.Command;
import ru.spacebattle.enums.CommandEnum;

import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class InterpretCommandServiceImpl implements InterpretCommandService {

    private final BaseProducer baseProducer;

    @Override
    public Optional<String> sendCommand(InterpretCommandRequestDto interpretCommandRequestDto) {

        try {
            interpretCommandRequestDto.getCommandsList().stream()
                    .map(commandDto -> new Command(
                            UUID.randomUUID(),
                            interpretCommandRequestDto.getPlayId(),
                            interpretCommandRequestDto.getUObjectId(),
                            CommandEnum.valueOf(commandDto.getCommandName()),
                            commandDto.getArgs()))
                    .forEach(baseProducer::sendCommand);
            return Optional.of("Команда отправлена на сервер");
        } catch (Exception e) {
            log.error("Ошибка помещения команды в очередь: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }
}
