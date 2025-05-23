package com.player.base;

import com.player.client.PlayerClient;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

@Listeners({AllureTestNg.class})
public class BaseTest {

    protected final String VALID_EDITOR = "supervisorLogin";

    protected PlayerClient playerClient;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        playerClient = new PlayerClient();
    }
}
