package com.player.tests;

import com.player.base.BaseTest;
import com.player.model.Player;
import com.player.utils.AssertHelper;
import com.player.utils.DataGenerator;
import com.player.utils.ResponseUtils;
import com.player.utils.TestDataHelper;
import io.qameta.allure.Issue;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class UpdatePlayerTest extends BaseTest {

    @Test(description = "Positive test: update player's login")
    public void testUpdatePlayerSuccessfully() {
        Player player = TestDataHelper.createAndReturnPlayer(VALID_EDITOR, playerClient);

        String updatedLogin = "updated_" + System.currentTimeMillis();
        Player update = Player.builder().login(updatedLogin).build();

        Response updateResponse = playerClient.updatePlayer(player.getId(), VALID_EDITOR, update);
        AssertHelper.assertStatusOk(updateResponse);

        Response getResponse = playerClient.getPlayer(player.getId());
        AssertHelper.assertStatusOk(getResponse);

        Player actual = ResponseUtils.toPlayer(getResponse);
        AssertHelper.assertLoginUpdated(player, actual, updatedLogin);
    }

    @Issue("BUG-10: Server allows updating player with invalid age")
    @Test(description = "Negative test: update player with invalid age")
    public void testUpdatePlayerInvalidAge() {
        Player player = TestDataHelper.createAndReturnPlayer(VALID_EDITOR, playerClient);
        Player update = DataGenerator.generatePlayerWithInvalidAge();

        Response response = playerClient.updatePlayer(player.getId(), VALID_EDITOR, update);
        int status = response.getStatusCode();

        assertTrue(status == 400 || status == 422, "Expected status 400 or 422 for invalid age (" + update.getAge() + "), but got: "
                + status + "\nResponse:\n" + response.getBody().asPrettyString());
    }
}
