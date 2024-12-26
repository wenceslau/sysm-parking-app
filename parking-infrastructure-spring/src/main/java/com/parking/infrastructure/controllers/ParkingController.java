package com.parking.infrastructure.controllers;

import com.parking.infrastructure.controllers.records.Presentation;
import com.parking.infrastructure.controllers.records.RegisterRequest;
import com.parking.infrastructure.services.ParkingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class ParkingController implements ParkingAPI {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @Override
    public ResponseEntity<?> open(int capacity) {

        parkingService.openParking(capacity);
        return ResponseEntity
                .ok()
                .body(Presentation.buildOpenResponse(capacity));

    }

    @Override
    public ResponseEntity<?> register(RegisterRequest registerRequest) {

        var registration = parkingService.registerLicensePlate(
                registerRequest.licensePlate(),
                registerRequest.vehicleType());

        parkingService.clearCacheByParameter(LocalDate.now());

        return ResponseEntity
                .ok()
                .body(Presentation.buildRegisterResponse(registration));
    }

    @Override
    public ResponseEntity<?> report() {

        var vehiclesParked = parkingService.vehiclesParked();
        var checkoutLog = parkingService.checkoutLog();

        return ResponseEntity
                .ok()
                .body(Presentation.buildReportResponse(vehiclesParked, checkoutLog));
    }

    @Override
    public ResponseEntity<?> report(LocalDate date) {

        var registrations = parkingService.findAllByCheckinDay(date);

        return ResponseEntity
                .ok()
                .body(Presentation.buildRegistrationsResponse(registrations));
    }
}
