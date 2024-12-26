package com.parking.infrastructure.services;

import com.parking.application.ParkingApp;
import com.parking.domain.Registration;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
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

    @Cacheable("findAllByCheckin")
    public List<Registration> findAllByCheckinDay(LocalDate date) {
        System.out.println("Cache miss, fetching from database");
        return parkingApp.findAllByCheckinDay(date);
    }

    @CacheEvict(value = "findAllByCheckin", key = "#date")
    public void clearCacheByParameter(LocalDate date) {
        System.out.println("Cache cleared");
    }

}
