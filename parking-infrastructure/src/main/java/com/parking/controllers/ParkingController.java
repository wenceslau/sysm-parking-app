package com.parking.controllers;

import com.parking.application.ParkingApp;
import com.parking.application.ParkingAppImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static com.parking.controllers.records.Presentation.*;

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
                .body(buildOpenResponse(capacity));

    }

    @Override
    public ResponseEntity<?> register(String licensePlate, String vehicleType) {

        var registration = parkingApp.registerLicensePlate(licensePlate, vehicleType);

        return ResponseEntity
                .ok()
                .body(buildRegisterResponse(registration));
    }

    @Override
    public ResponseEntity<?> report() {

        var vehiclesParked = parkingApp.vehiclesParked();
        var checkoutLog = parkingApp.checkoutLog();

        return ResponseEntity
                .ok()
                .body(buildReportResponse(vehiclesParked, checkoutLog));
    }
}
