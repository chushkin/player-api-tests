package com.player.utils;

import com.player.model.Player;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {

    public static Player generateValidPlayer() {
        return Player.builder()
                .age(ThreadLocalRandom.current().nextInt(17, 60))
                .gender("male")
                .login("login_" + UUID.randomUUID())
                .password("Pass1234")
                .role("user")
                .screenName("screen_" + UUID.randomUUID())
                .build();
    }

    public static Player generateInvalidPlayerMissingRequiredFields() {
        return Player.builder().build();
    }

    public static Player generatePlayerWithInvalidAge() {
        Player player = generateValidPlayer();
        player.setAge(generateInvalidAge());
        return player;
    }

    public static int generateInvalidAge() {
        return Math.random() < 0.5
                ? ThreadLocalRandom.current().nextInt(0, 16)    // too young
                : ThreadLocalRandom.current().nextInt(61, 100); // too old
    }
}
