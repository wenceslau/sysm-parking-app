package com.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
        var url = "jdbc:h2:file:d:/tmp/data/parking";
        var user = "sa";
        var password = "";

        instance = DriverManager.getConnection(url, user, password);
        return instance;
    }

}
