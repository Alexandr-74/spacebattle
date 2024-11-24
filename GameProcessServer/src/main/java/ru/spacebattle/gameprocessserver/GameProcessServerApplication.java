package ru.spacebattle.gameprocessserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.spacebattle.gameprocessserver.config.GameCommandInitializer;

@SpringBootApplication
public class GameProcessServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameProcessServerApplication.class, args);
    }

}
