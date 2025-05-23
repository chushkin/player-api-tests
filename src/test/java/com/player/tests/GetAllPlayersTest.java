package com.player.tests;

import com.player.base.BaseTest;
import com.player.model.Player;
import com.player.utils.AssertHelper;
import com.player.utils.ResponseUtils;
import com.player.utils.TestDataHelper;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

public class GetAllPlayersTest extends BaseTest {

    @Test(description = "Positive test: get all players and verify created players are present")
    public void testGetAllPlayers() {
        Player player1 = TestDataHelper.createAndReturnPlayer(VALID_EDITOR, playerClient);
        Player player2 = TestDataHelper.createAndReturnPlayer(VALID_EDITOR, playerClient);

        Response response = playerClient.getAllPlayers();
        AssertHelper.assertStatusOk(response);

        List<Player> players = ResponseUtils.toPlayerList(response);

        AssertHelper.assertPlayerInList(player1, players);
        AssertHelper.assertPlayerInList(player2, players);
    }
}
