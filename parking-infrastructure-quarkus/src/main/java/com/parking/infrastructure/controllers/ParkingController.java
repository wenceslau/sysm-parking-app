package com.parking.infrastructure.controllers;

import com.parking.infrastructure.configuration.security.JWTokenFactory;
import com.parking.infrastructure.controllers.records.AuthRequest;
import com.parking.infrastructure.controllers.records.Presentation;
import com.parking.infrastructure.controllers.records.RegisterRequest;
import com.parking.infrastructure.repositories.UserRepository;
import com.parking.infrastructure.services.ParkingService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import static com.parking.infrastructure.configuration.security.JWTokenFactory.encode;
import static com.parking.infrastructure.configuration.security.JWTokenFactory.generateToken;
import static com.parking.infrastructure.controllers.records.Presentation.*;

@Path("/parking")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParkingController {

    @Inject
    ParkingService parkingService;

    @Inject
    UserRepository userRepository;

    @GET
    public Response status() {

        boolean isOpen = parkingService.isOpen();
        int capacity = 0;
        int occupation = 0;
        if (isOpen){
            capacity = parkingService.capacity();
            occupation = parkingService.vehiclesParked().size();
        }

        var statusResponse = Presentation.buildStatusResponse(isOpen, capacity, occupation);

        return Response
                .ok()
                .entity(statusResponse)
                .build();
    }

    @POST
    public Response open(int capacity) {

        parkingService.openParking(capacity);

        return Response
                .ok()
                .entity(Presentation.buildOpenResponse(capacity))
                .build();

    }

    @POST
    @Path("/register")
    public Response register(RegisterRequest registerRequest) {

        var registration = parkingService.registerLicensePlate(
                registerRequest.licensePlate(),
                registerRequest.vehicleType());

        parkingService.clearCacheByParameter(LocalDate.now());
        var vehiclesParked = parkingService.vehiclesParked();
        var checkoutLog = parkingService.checkoutLog();

        return Response
                .ok()
                .entity(buildRegisterResponse(registration, vehiclesParked, checkoutLog))
                .build();
    }

    @GET
    public Response checkInReport() {

        var vehiclesParked = parkingService.vehiclesParked();

        return Response
                .ok()
                .entity(Presentation.buildCheckInResponse(vehiclesParked))
                .build();
    }

    @GET
    public Response checkOutReport() {

        var checkoutLog = parkingService.checkoutLog();
        return Response
                .ok()
                .entity(Presentation.buildCheckOutResponse(checkoutLog))
                .build();
    }

    @GET
    @Path("/report/{date}")
    public Response report(LocalDate date) {

        var registrations = parkingService.findAllByCheckinDay(date);

        return Response
                .ok()
                .entity(Presentation.buildRegistrationsResponse(registrations))
                .build();
    }

    @POST
    @Path("/auth")
    public Response auth(AuthRequest authRequest) {
        // Authenticate the user
        var user = userRepository.findByUsername(authRequest.username());

        if (user.isEmpty() || !user.get().getPassword().equals(encode(authRequest.password()))) {
            return Response
                    .status(401)
                    .build();
        }

        String token = generateToken(authRequest.username(), Set.of(user.get().getRole()));

        return Response
                .status(200)
                .entity(Map.of("token", token))
                .build();
    }

}
