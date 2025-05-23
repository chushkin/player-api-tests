package com.player.tests;

import com.player.base.BaseTest;
import com.player.model.Player;
import com.player.utils.AssertHelper;
import com.player.utils.DataGenerator;
import com.player.utils.ResponseUtils;
import io.qameta.allure.Issue;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class DeletePlayerTest extends BaseTest {

    @Test(description = "Positive test: delete existing player")
    public void testDeletePlayerSuccessfully() {
        Player player = DataGenerator.generateValidPlayer();
        Long id = ResponseUtils.toPlayer(playerClient.createPlayer(player, VALID_EDITOR)).getId();

        Response deleteResponse = playerClient.deletePlayer(id, VALID_EDITOR);
        AssertHelper.assertStatusNoContent(deleteResponse);

        Response getDeletedResponse = playerClient.getPlayer(id);
        AssertHelper.assertPlayerNotFound(getDeletedResponse);
    }

    @Issue("BUG-11: Server returns 403 instead of 404 when deleting non-existent user")
    @Test(description = "Negative test: delete non-existent player")
    public void testDeletePlayerNotFound() {
        Response response = playerClient.deletePlayer(999999L, VALID_EDITOR);
        int statusCode = response.getStatusCode();

        assertTrue(statusCode == 404, "Expected status code 404 when deleting non-existent player, but got: " + statusCode + "\nResponse:\n" + response.getBody().asPrettyString());
    }
}
