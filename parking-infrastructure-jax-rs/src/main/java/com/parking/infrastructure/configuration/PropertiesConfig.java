package com.parking.infrastructure.configuration;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesConfig {

    private static Properties properties;

    public static String getProperty(String key) {
        if (properties == null) {
            readProperties();
        }
        return properties.getProperty(key);
    }

    private static void readProperties() {
        try (InputStream input = PropertiesConfig.class.getClassLoader().getResourceAsStream("app.properties")) {
            if (input == null) {
                throw new IOException("Properties file not found");
            }
            // Load the properties file
            properties = new Properties();
            properties.load(input);

        } catch (IOException ex) {
            throw new RuntimeException("Error reading properties file", ex);
        }
    }
}
