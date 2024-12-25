package com.parking.application;

import com.parking.Registration;

import java.util.List;

public interface ParkingApp {

    void openParking(int capacity);

    Registration registerLicensePlate(String licensePlate, String vehicleType);

    List<Registration> vehiclesParked();

    List<Registration> checkoutLog();
}
