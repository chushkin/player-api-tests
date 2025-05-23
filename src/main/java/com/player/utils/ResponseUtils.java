package com.player.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.player.model.Player;
import io.restassured.response.Response;

import java.util.List;

public class ResponseUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Player toPlayer(Response response) {
        try {
            String json = response.getBody().asString();

            if (json == null || json.trim().isEmpty() || json.trim().equals("{}")) {
                return new Player();
            }

            return objectMapper.readValue(json, Player.class);

        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize Player object: " + e.getMessage(), e);
        }
    }

    public static List<Player> toPlayerList(Response response) {
        try {
            JsonNode root = objectMapper.readTree(response.getBody().asString());
            JsonNode playersNode = root.get("players");

            return objectMapper.readValue(playersNode.toString(), new TypeReference<List<Player>>() {});

        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize player list", e);
        }
    }
}
