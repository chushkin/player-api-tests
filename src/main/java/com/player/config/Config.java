package com.player.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                props.load(input);
            } else {
                throw new RuntimeException("config.properties not found in resources");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    private static String get(String key) {
        return System.getProperty(key, props.getProperty(key));
    }

    public static String getBaseUrl() {
        return get("base.url");
    }

    public static String getEnvironment() {
        return get("test.environment");
    }

    public static int getThreadCount() {
        return Integer.parseInt(get("thread.count"));
    }
}
