package com.parking;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class Registration {

    private final String id;
    private final Vehicle vehicle;
    private final LocalDateTime checkIn;

    private LocalDateTime checkOut;
    private Duration duration;
    private double amount;

    public Registration(Vehicle vehicle, LocalDateTime checkin) {
        this.id = UUID.randomUUID().toString();
        this.vehicle = vehicle;
        this.checkIn = checkin;
        validate();
    }

    public String getId() {
        return id;
    }

    public void checkOut(LocalDateTime checkOut) {

        if (checkOut == null) {
            throw new IllegalArgumentException("Check-out date is required");
        }
        if (checkOut.isBefore(checkIn)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }

        this.checkOut = checkOut;
        this.duration = Duration.between(checkIn, checkOut);
        this.amount = vehicle.amountToPay(duration.toMinutes());
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public Duration getDuration() {
        return duration;
    }

    public double getAmount() {
        return amount;
    }

    private void validate() {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle is required");
        }
        if (checkIn == null) {
            throw new IllegalArgumentException("Check-in date is required");
        }
    }

    // This constructor is used to create a Registration object from the database
    private Registration(String id, Vehicle vehicle, LocalDateTime checkIn, LocalDateTime checkOut, Duration duration, double amount) {
        this.id = id;
        this.vehicle = vehicle;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.duration = duration;
        this.amount = amount;
    }

    public static Registration from(String id,
                                    String vehicleType,
                                    String licensePlate,
                                    double rate,
                                    LocalDateTime checkIn,
                                    LocalDateTime checkOut,
                                    Duration duration,
                                    double amount) {
        if (id == null || vehicleType == null || licensePlate == null || checkIn == null) {
            throw new IllegalArgumentException("All fields are required");
        }
        var type = VehicleType.valueOf(vehicleType);
        var vehicle = type.getVehicle(licensePlate, rate);
        return new Registration(id, vehicle, checkIn, checkOut, duration, amount);
    }

}
