package com.parking.configuration;

import com.parking.dao.ConnectionDB;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@ApplicationPath("/")
public class ApplicationConfig extends Application {

    // Configuração base da API REST
    static {
        System.out.println("ApplicationConfig carregado com sucesso!");
        initializerDatabase();
    }

    private static void initializerDatabase() {
        try {
            var connection = ConnectionDB.getInstance();
            var sql = """
                         CREATE TABLE IF NOT EXISTS rates (
                        id VARCHAR(255) NOT NULL,
                        rate FLOAT(53),
                        vehicle_type VARCHAR(255),
                        PRIMARY KEY (id)
                    )
                    """;
            var statement = connection.prepareStatement(sql);
            statement.execute();
            statement.close();

            sql = """
                    CREATE TABLE IF NOT EXISTS registrations (
                        id VARCHAR(255) NOT NULL,
                        amount FLOAT(53),
                        check_in TIMESTAMP(6),
                        check_out TIMESTAMP(6),
                        license_plate VARCHAR(255),
                        rate FLOAT(53),
                        vehicle_type VARCHAR(255),
                        PRIMARY KEY (id)
                    )
                    """;

            statement = connection.prepareStatement(sql);
            statement.execute();
            statement.close();

            sql = "DELETE FROM rates WHERE 1=1";
            statement = connection.prepareStatement(sql);
            statement.execute();
            statement.close();

            sql = "INSERT INTO rates (id, rate, vehicle_type) VALUES ('1', 5.0, 'CAR')";
            statement = connection.prepareStatement(sql);
            statement.execute();
            statement.close();

            sql = "INSERT INTO rates (id, rate, vehicle_type) VALUES ('2', 5.0, 'MOTORCYCLE')";
            statement = connection.prepareStatement(sql);
            statement.execute();
            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
