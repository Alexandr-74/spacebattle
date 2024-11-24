package ru.spacebattle.gameprocessserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.spacebattle.enums.CommandEnum;
import ru.spacebattle.gameprocessserver.service.handler.CommandsHandler;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class GameProcessServerConfig {

    @Bean
    public Map<CommandEnum, CommandsHandler> commandsHandlers(List<CommandsHandler> commandsHandlers) {
        return commandsHandlers.stream().collect(Collectors.toMap(CommandsHandler::getCommand, Function.identity()));
    }
}
