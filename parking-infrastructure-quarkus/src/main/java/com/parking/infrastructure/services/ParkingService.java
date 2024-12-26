package com.parking.infrastructure.services;

import com.parking.application.ParkingApp;
import com.parking.domain.Registration;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;


import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class ParkingService {

    private final ParkingApp parkingApp;

    public ParkingService(ParkingApp parkingApp) {
        this.parkingApp = parkingApp;
    }

    public void openParking(int capacity) {
        parkingApp.openParking(capacity);
    }

    public Registration registerLicensePlate(String licensePlate, String vehicleType) {
        return parkingApp.registerLicensePlate(licensePlate, vehicleType);
    }

    public List<Registration> vehiclesParked() {
        return parkingApp.vehiclesParked();
    }

    public List<Registration> checkoutLog() {
        return parkingApp.checkoutLog();
    }

    @CacheResult(cacheName = "findAllByCheckin")
    public List<Registration> findAllByCheckinDay(LocalDate date) {
        System.out.println("Cache miss, fetching from database");
        return parkingApp.findAllByCheckinDay(date);
    }

    @CacheInvalidate(cacheName = "findAllByCheckin")
    public void clearCacheByParameter(LocalDate date) {
        System.out.println("Cache cleared");
    }

}
