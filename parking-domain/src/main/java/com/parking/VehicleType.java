package com.parking;

import com.parking.vehicles.Car;
import com.parking.vehicles.Motorcycle;

import java.util.Arrays;

public enum VehicleType {
    CAR,
    MOTORCYCLE;

    public Vehicle getVehicle(String licensePlate, double rate) {
        return switch (this) {
            case CAR -> new Car(licensePlate, rate);
            case MOTORCYCLE -> new Motorcycle(licensePlate, rate);
        };
    }

    public static VehicleType converter(String vehicleType) {
        try {
            return VehicleType.valueOf(vehicleType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid vehicle type. Types available: " + Arrays.toString(VehicleType.values()));
        }
    }

}
