package com.parking.infrastructure.controllers;

import com.parking.infrastructure.controllers.records.RegisterRequest;
import com.parking.infrastructure.services.ParkingService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;

import static com.parking.infrastructure.controllers.records.Presentation.*;

@Path("/parking")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParkingController {

    @Inject
    ParkingService parkingService;

    @POST
    @Path("/open/{capacity}")
    public Response open(@PathParam("capacity") int capacity){
        parkingService.openParking(capacity);

        return Response
                .ok()
                .entity(buildOpenResponse(capacity))
                .build();
    }

    @POST
    @Path("/register")
    public Response register(@Valid RegisterRequest registerRequest){

        var registration = parkingService.registerLicensePlate(
                registerRequest.licensePlate(),
                registerRequest.vehicleType());

        parkingService.clearCacheByParameter(LocalDate.now());

        return Response
                .ok()
                .entity(buildRegisterResponse(registration))
                .build();
    }

    @GET
    @Path("/report")
    public Response report(){

        var vehiclesParked = parkingService.vehiclesParked();
        var checkoutLog = parkingService.checkoutLog();

        return Response
                .ok()
                .entity(buildReportResponse(vehiclesParked, checkoutLog))
                .build();
    }

    @GET
    @Path("/report/{date}")
    public Response report(@PathParam("date") String date){

        var localDate = LocalDate.parse(date);
        var registrations = parkingService.findAllByCheckinDay(localDate);

        return Response
                .ok()
                .entity(buildRegistrationsResponse(registrations))
                .build();
    }

}
