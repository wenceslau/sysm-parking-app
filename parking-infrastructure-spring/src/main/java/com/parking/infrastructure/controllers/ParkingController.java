package com.parking.infrastructure.controllers;

import com.parking.infrastructure.configuration.security.JWTokenService;
import com.parking.infrastructure.controllers.records.AuthRequest;
import com.parking.infrastructure.controllers.records.Presentation;
import com.parking.infrastructure.controllers.records.RegisterRequest;
import com.parking.infrastructure.services.ParkingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
public class ParkingController implements ParkingAPI {

    private final ParkingService parkingService;
    private final JWTokenService JWTokenService;
    private final AuthenticationManager authenticationManager;

    public ParkingController(ParkingService parkingService, JWTokenService JWTokenService, AuthenticationManager authenticationManager) {
        this.parkingService = parkingService;
        this.JWTokenService = JWTokenService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public ResponseEntity<?> status() {

        boolean isOpen = parkingService.isOpen();
        int capacity = 0;
        int occupation = 0;
        if (isOpen){
            capacity = parkingService.capacity();
            occupation = parkingService.vehiclesParked().size();
        }

        var statusResponse = Presentation.buildStatusResponse(isOpen, capacity, occupation);

        return ResponseEntity
                .ok()
                .body(statusResponse);
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
        var vehiclesParked = parkingService.vehiclesParked();
        var checkoutLog = parkingService.checkoutLog();

        return ResponseEntity
                .ok()
                .body(Presentation.buildRegisterResponse(registration, vehiclesParked, checkoutLog));
    }

    @Override
    public ResponseEntity<?> checkInReport() {

        var vehiclesParked = parkingService.vehiclesParked();

        return ResponseEntity
                .ok()
                .body(Presentation.buildCheckInResponse(vehiclesParked));
    }

    @Override
    public ResponseEntity<?> checkOutReport() {

        var checkoutLog = parkingService.checkoutLog();

        return ResponseEntity
                .ok()
                .body(Presentation.buildCheckOutResponse(checkoutLog));
    }

    @Override
    public ResponseEntity<?> report(LocalDate date) {

        var registrations = parkingService.findAllByCheckinDay(date);

        return ResponseEntity
                .ok()
                .body(Presentation.buildRegistrationsResponse(registrations));
    }

    @Override
    public ResponseEntity<?> auth(AuthRequest authRequest) {
        // Authenticate the user

        var userAuth = new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password());

        var authentication = authenticationManager.authenticate(userAuth);

        var token = JWTokenService.generateToken(authentication.getName());

        return ResponseEntity
                .status(200)
                .body(Map.of("token", token));
    }
}
