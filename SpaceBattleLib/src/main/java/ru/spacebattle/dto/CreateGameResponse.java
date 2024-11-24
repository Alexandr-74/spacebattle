package ru.spacebattle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.spacebattle.entities.UObject;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGameResponse {
    UUID gameId;
    Map<UUID, UObject> uObjects;
}
