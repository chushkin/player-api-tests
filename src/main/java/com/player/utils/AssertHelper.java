package com.player.utils;

import com.player.model.Player;
import io.restassured.response.Response;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static org.testng.Assert.assertTrue;

public class AssertHelper {

    public static void assertStatusOk(Response response) {
        int status = response.getStatusCode();
        if (status != 200) {
            throw new AssertionError("Expected status code 200, but got: " + status +
                    "\nResponse: " + response.getBody().asPrettyString());
        }
    }

    public static void assertStatusNoContent(Response response) {
        int status = response.getStatusCode();
        if (status != 204) {
            throw new AssertionError("Expected status code 204 (No Content), but got: " + status +
                    "\nResponse: " + response.getBody().asPrettyString());
        }
    }

    public static void assertPlayerEquals(Player expected, Player actual) {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(actual.getLogin(), expected.getLogin(), "'login' does not match");
        softAssert.assertEquals(actual.getScreenName(), expected.getScreenName(), "'screenName' does not match");
        softAssert.assertEquals(actual.getPassword(), expected.getPassword(), "'password' does not match");
        softAssert.assertEquals(actual.getGender(), expected.getGender(), "'gender' does not match");
        softAssert.assertEquals(actual.getAge(), expected.getAge(), "'age' does not match");
        softAssert.assertEquals(actual.getRole(), expected.getRole(), "'role' does not match");

        softAssert.assertAll("Player objects do not match");
    }

    public static void assertLoginUpdated(Player original, Player updated, String expectedLogin) {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(updated.getId(), original.getId(), "ID must remain unchanged");
        softAssert.assertEquals(updated.getLogin(), expectedLogin, "Login must be updated");

        softAssert.assertEquals(updated.getRole(), original.getRole(), "Role must not change");
        softAssert.assertEquals(updated.getGender(), original.getGender(), "Gender must not change");
        softAssert.assertEquals(updated.getAge(), original.getAge(), "Age must not change");
        softAssert.assertEquals(updated.getScreenName(), original.getScreenName(), "ScreenName must not change");

        softAssert.assertAll("Inconsistencies found after login update");
    }

    public static void assertPlayerNotFound(Response response) {
        int status = response.getStatusCode();

        if (status == 200) {
            Player player = ResponseUtils.toPlayer(response);
            boolean allFieldsNull = player.getId() == null &&
                    player.getLogin() == null &&
                    player.getScreenName() == null &&
                    player.getAge() == null &&
                    player.getGender() == null &&
                    player.getRole() == null;

            if (!allFieldsNull) {
                throw new AssertionError("Expected empty Player object (all fields null), but got:\n"
                        + response.getBody().asPrettyString());
            }
        } else {
            throw new AssertionError("Expected status 200 with null fields or 404, but got: " + status +
                    "\nResponse: " + response.getBody().asPrettyString());
        }
    }

    public static void assertPlayerInList(Player expected, List<Player> actualList) {
        boolean found = actualList.stream()
                .anyMatch(p -> expected.getId().equals(p.getId()));
        assertTrue(found, "Player with id = " + expected.getId() + " was not found in the list");
    }
}
