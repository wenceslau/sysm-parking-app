package com.parking.infrastructure.dao;

import com.parking.domain.ParkingGateway;
import com.parking.domain.Registration;
import com.parking.domain.VehicleType;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ParkingGatewayDB implements ParkingGateway {

    @Override
    public void save(Registration registration) {

        try {

            if (registration.getCheckOut() == null) {
                insert(registration);
            } else {
                update(registration);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getRateByType(VehicleType vehicleType) {

        try {
            var connection = ConnectionDB.getInstance();
            var sql = "SELECT rate FROM rates WHERE vehicle_type = ?";

            var statement = connection.prepareStatement(sql);
            statement.setString(1, vehicleType.name());

            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("rate");
            } else {
                throw new RuntimeException("Rate not found for vehicle type: " + vehicleType);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Registration> loadAllByCurrentDay() {
        var list = new ArrayList<Registration>();
        try {

            var connection = ConnectionDB.getInstance();
            var localDateTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
            var currentDay = java.sql.Timestamp.valueOf(localDateTime);

            var sql = "SELECT * FROM registrations WHERE check_in >= ?";
            var statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, currentDay);

            var resultSet = statement.executeQuery();
            while (resultSet.next()) {

                var checkin = resultSet.getTimestamp("check_in");
                var checkout = resultSet.getTimestamp("check_out");

                Duration duration = null;
                if (checkout != null) {
                    duration = Duration.between(checkin.toLocalDateTime(), checkout.toLocalDateTime());
                }

                var registration = Registration.from(
                        resultSet.getString("id"),
                        resultSet.getString("vehicle_type"),
                        resultSet.getString("license_plate"),
                        resultSet.getDouble("rate"),
                        checkin.toLocalDateTime(),
                        checkout != null ? checkout.toLocalDateTime() : null,
                        duration,
                        resultSet.getDouble("amount")
                );
                list.add(registration);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    private void insert(Registration registration) throws SQLException {
        var connection = ConnectionDB.getInstance();

        var sql = "INSERT INTO registrations (id, vehicle_type, license_plate,  check_in, rate) VALUES (?, ?, ?, ?, ?)";

        var statement = connection.prepareStatement(sql);

        statement.setString(1, registration.getId());
        statement.setString(2, registration.getVehicle().getClass().getSimpleName().toUpperCase());
        statement.setString(3, registration.getVehicle().getLicensePlate());
        statement.setTimestamp(4, java.sql.Timestamp.valueOf(registration.getCheckIn()));
        statement.setDouble(5, registration.getVehicle().getRate());

        statement.executeUpdate();
    }

    private void update(Registration registration) throws SQLException {
        var connection = ConnectionDB.getInstance();

        var sql = "UPDATE registrations SET check_out = ?, amount = ? WHERE id = ?";

        var statement = connection.prepareStatement(sql);

        statement.setTimestamp(1, java.sql.Timestamp.valueOf(registration.getCheckOut()));
        statement.setDouble(2, registration.getAmount());
        statement.setString(3, registration.getId());

        statement.executeUpdate();
    }
}
