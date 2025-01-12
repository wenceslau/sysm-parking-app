package com.parking.application;

import com.parking.domain.Registration;

import java.time.LocalDate;
import java.util.List;

public interface ParkingApp {

    boolean isOpen();

    void openParking(int capacity);

    Registration registerLicensePlate(String licensePlate, String vehicleType);

    List<Registration> findAllByCheckinDay(LocalDate date);

    List<Registration> vehiclesParked();

    List<Registration> checkoutLog();
}
