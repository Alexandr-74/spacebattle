package ru.spacebattle.agent.connector;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.spacebattle.agent.dto.*;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static ru.spacebattle.agent.constatnts.URLConstants.CREATE_GAME_AUTH_URL;
import static ru.spacebattle.agent.constatnts.URLConstants.SIGN_UP_URL;
import static ru.spacebattle.agent.constatnts.URLParams.BASE_HOST;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationConnector {

    @Value("${agent.authorizationServer.url}")
    private String baseHost;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    public ResponseMessage signUp(SignUpRequest signUpRequest) {
        try {
            URI url = UriComponentsBuilder.fromUriString(SIGN_UP_URL)
                    .build(Map.of(
                            BASE_HOST, baseHost
                    ));

            String host = URLDecoder.decode(url.toString(), StandardCharsets.UTF_8);

            String body = objectMapper.writeValueAsString(signUpRequest);

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            HttpEntity<String> entity = new HttpEntity<>(body, headers);

            restTemplate.postForEntity(host, entity, String.class);

            return new SuccessResponse("Registered");
        } catch (Exception e) {
            log.error("Auth error: {}", e.getMessage(), e);
            return new ErrorResponse(e.getMessage());
        }
    }

    public JwtAuthenticationResponse createGame(CreateGameRequest createGameRequest) {

        try {
            URI url = UriComponentsBuilder.fromUriString(CREATE_GAME_AUTH_URL)
                    .build(Map.of(
                            BASE_HOST, baseHost
                    ));

            String host = URLDecoder.decode(url.toString(), StandardCharsets.UTF_8);

            String body = objectMapper.writeValueAsString(createGameRequest);

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            HttpEntity<String> entity = new HttpEntity<>(body, headers);

            ResponseEntity<JwtAuthenticationResponse> response = restTemplate.postForEntity(host, entity, JwtAuthenticationResponse.class);

            return response.getBody();
        } catch (Exception e) {
            log.error("Create game auth error: {}", e.getMessage(), e);
            throw new RuntimeException("Create game auth error: ".concat(e.getMessage()));
        }
    }
}
