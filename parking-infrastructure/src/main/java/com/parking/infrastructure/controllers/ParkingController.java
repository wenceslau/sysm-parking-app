package com.parking.infrastructure.controllers;

import com.parking.application.ParkingApp;
import com.parking.infrastructure.controllers.records.Presentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingController implements ParkingAPI {

    private final ParkingApp parkingApp;

    public ParkingController(ParkingApp parkingApp) {
        this.parkingApp = parkingApp;
    }

    @Override
    public ResponseEntity<?> open(int capacity) {

        parkingApp.openParking(capacity);
        return ResponseEntity
                .ok()
                .body(Presentation.buildOpenResponse(capacity));

    }

    @Override
    public ResponseEntity<?> register(String licensePlate, String vehicleType) {

        var registration = parkingApp.registerLicensePlate(licensePlate, vehicleType);

        return ResponseEntity
                .ok()
                .body(Presentation.buildRegisterResponse(registration));
    }

    @Override
    public ResponseEntity<?> report() {

        var vehiclesParked = parkingApp.vehiclesParked();
        var checkoutLog = parkingApp.checkoutLog();

        return ResponseEntity
                .ok()
                .body(Presentation.buildReportResponse(vehiclesParked, checkoutLog));
    }
}
