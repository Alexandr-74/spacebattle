package ru.spacebattle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.spacebattle.entities.UObject;

import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateGameResourcesRequest {
    private Map<UUID, UObject> uObjectMap;
    private UUID gameId;
}
