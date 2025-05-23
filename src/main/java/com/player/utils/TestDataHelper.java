package com.player.utils;

import com.player.client.PlayerClient;
import com.player.model.Player;
import io.restassured.response.Response;

public class TestDataHelper {

    public static Player createAndReturnPlayer(String editorLogin, PlayerClient playerClient) {
        Player player = DataGenerator.generateValidPlayer();

        Response response = playerClient.createPlayer(player, editorLogin);
        AssertHelper.assertStatusOk(response);

        Long id = ResponseUtils.toPlayer(response).getId();
        player.setId(id);

        return player;
    }
}
