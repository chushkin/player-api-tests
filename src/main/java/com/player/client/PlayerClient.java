package com.player.client;

import com.player.config.Config;
import com.player.model.Player;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PlayerClient {

    static {
        RestAssured.baseURI = Config.getBaseUrl();
    }

    public Response createPlayer(Player player, String editorLogin) {
        return given()
                .queryParam("age", player.getAge())
                .queryParam("gender", player.getGender())
                .queryParam("login", player.getLogin())
                .queryParam("password", player.getPassword())
                .queryParam("role", player.getRole())
                .queryParam("screenName", player.getScreenName())
                .get("/player/create/" + editorLogin);
    }

    public Response getPlayer(Long playerId) {
        Map<String, Long> body = new HashMap<>();
        body.put("playerId", playerId);

        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/player/get");
    }

    public Response updatePlayer(Long id, String editorLogin, Player player) {
        return given()
                .contentType(ContentType.JSON)
                .body(player)
                .patch("/player/update/" + editorLogin + "/" + id);
    }

    public Response deletePlayer(Long playerId, String editorLogin) {
        Map<String, Long> body = new HashMap<>();
        body.put("playerId", playerId);

        return given()
                .contentType(ContentType.JSON)
                .pathParam("editor", editorLogin)
                .body(body)
                .delete("/player/delete/{editor}");
    }

    public Response getAllPlayers() {
        return given()
                .get("/player/get/all");
    }
}
