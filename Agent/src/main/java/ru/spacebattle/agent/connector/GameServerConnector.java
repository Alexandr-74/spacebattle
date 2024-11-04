package ru.spacebattle.agent.connector;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.spacebattle.agent.dto.JwtAuthenticationResponse;
import ru.spacebattle.dto.CreateGameRequest;
import ru.spacebattle.dto.CreateGameResponse;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static ru.spacebattle.agent.constatnts.URLConstants.CREATE_GAME_SERVER_URL;
import static ru.spacebattle.agent.constatnts.URLParams.BASE_HOST;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameServerConnector {

    public static final String BEARER_PREFIX = "Bearer ";
    @Value("${agent.gameServer.url}")
    private String baseHost;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CreateGameResponse createGame(JwtAuthenticationResponse jwtAuthenticationResponse, CreateGameRequest createGameRequest) {

        try {
            URI url = UriComponentsBuilder.fromUriString(CREATE_GAME_SERVER_URL)
                    .build(Map.of(
                            BASE_HOST, baseHost
                    ));

            String host = URLDecoder.decode(url.toString(), StandardCharsets.UTF_8);


            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            headers.set(HttpHeaders.AUTHORIZATION, BEARER_PREFIX.concat(jwtAuthenticationResponse.getToken()));
            HttpEntity<CreateGameRequest> entity = new HttpEntity<>(createGameRequest, headers);

            ResponseEntity<String> response = restTemplate.exchange(host, HttpMethod.POST, entity, String.class);

            return objectMapper.readValue(response.getBody(), CreateGameResponse.class);
        } catch (Exception e) {
            log.error("Create game auth error: {}", e.getMessage(), e);
            return null;
        }
    }
}
