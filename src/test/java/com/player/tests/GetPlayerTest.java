package com.player.tests;

import com.player.base.BaseTest;
import com.player.model.Player;
import com.player.utils.AssertHelper;
import com.player.utils.ResponseUtils;
import com.player.utils.TestDataHelper;
import io.qameta.allure.Issue;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GetPlayerTest extends BaseTest {

    @Test(description = "Positive test: get player by ID")
    public void testGetPlayerSuccessfully() {
        Player player = TestDataHelper.createAndReturnPlayer(VALID_EDITOR, playerClient);
        Response response = playerClient.getPlayer(player.getId());

        AssertHelper.assertStatusOk(response);
        Player actual = ResponseUtils.toPlayer(response);
        AssertHelper.assertPlayerEquals(player, actual);
    }

    @Issue("BUG-10: Server returns 200 OK for non-existent user")
    @Test(description = "Negative test: get non-existent player")
    public void testGetPlayerNotFound() {
        Response response = playerClient.getPlayer(999999L);
        int statusCode = response.getStatusCode();

        assertEquals(statusCode, 404, "Expected status 404 when getting non-existent player, but got: " + statusCode +
                "\nResponse:\n" + response.getBody().asPrettyString());
    }
}
