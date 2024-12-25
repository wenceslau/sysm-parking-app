package com.parking.dao;

import com.parking.configuration.PropertiesConfig;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.parking.configuration.PropertiesConfig.getProperty;

@ApplicationScoped
public class ConnectionDB {

    private static Connection instance;

    private ConnectionDB() {
    }

    public static Connection getInstance() throws SQLException {

        if (instance != null) {
            try {
                if (!instance.isClosed()) {
                    return instance;
                }
            } catch (SQLException e) {
                instance = null;
            }
        }

        //Get data connection from application.properties
        var url = getProperty("app.database.url");
        var user = getProperty("app.database.user");
        var password = getProperty("app.database.password");

        instance = DriverManager.getConnection(url, user, password);
        return instance;
    }

}
