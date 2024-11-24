package ru.spacebattle.gameserver.connector;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.spacebattle.dto.CreateGameRequest;
import ru.spacebattle.dto.CreateGameResourcesRequest;
import ru.spacebattle.dto.CreateGameResponse;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static ru.spacebattle.gameserver.constants.URLConstants.BASE_HOST;
import static ru.spacebattle.gameserver.constants.URLConstants.CREATE_GAME_RESOURCES_URL;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameProcessConnector {

    @Value("${server.gameProcessServer.url}")
    private String baseHost;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public String createGameResourcesRequest(CreateGameResourcesRequest createGameResourcesRequest) {
        try {
            URI url = UriComponentsBuilder.fromUriString(CREATE_GAME_RESOURCES_URL)
                    .build(Map.of(
                            BASE_HOST, baseHost
                    ));

            String host = URLDecoder.decode(url.toString(), StandardCharsets.UTF_8);


            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            HttpEntity<CreateGameResourcesRequest> entity = new HttpEntity<>(createGameResourcesRequest, headers);

            ResponseEntity<String> response = restTemplate.exchange(host, HttpMethod.POST, entity, String.class);

            return response.getBody();
        } catch (Exception e) {
            log.error("Create game resources error: {}", e.getMessage(), e);
            return null;
        }
    }
}
