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

public class CreatePlayerTest extends BaseTest {

    @Issue("BUG-5: Response contains null fields after successful creation")
    @Test(description = "Positive test: create player with valid data")
    public void testCreatePlayerSuccessfully() {
        Player player = DataGenerator.generateValidPlayer();

        Response response = playerClient.createPlayer(player, VALID_EDITOR);
        AssertHelper.assertStatusOk(response);

        Player actual = ResponseUtils.toPlayer(response);
        AssertHelper.assertPlayerEquals(player, actual);
    }

    @Test(description = "Negative test: create player with missing required fields")
    public void testCreatePlayerMissingRequiredFields() {
        Player player = DataGenerator.generateInvalidPlayerMissingRequiredFields();

        Response response = playerClient.createPlayer(player, VALID_EDITOR);
        int statusCode = response.getStatusCode();

        assertTrue(statusCode == 400, "Expected status code 400, but got: " + statusCode + "\nResponse:\n" + response.getBody().asPrettyString());
    }
}
