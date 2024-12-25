package com.parking.controllers;

import com.parking.application.ParkingApp;
import com.parking.application.ParkingAppImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static com.parking.controllers.records.Presentation.*;

@Path("/parking")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParkingAPI {

    @Inject
    ParkingApp parkingApp;

    @POST
    @Path("/open/{capacity}")
    public Response open(@PathParam("capacity") int capacity){
        parkingApp.openParking(capacity);

        return Response
                .ok()
                .entity(buildOpenResponse(capacity))
                .build();
    }

    @POST
    @Path("/register/{licensePlate}/{vehicleType}")
    public Response register(@PathParam("licensePlate") String licensePlate, @PathParam("vehicleType") String vehicleType){
        var registration = parkingApp.registerLicensePlate(licensePlate, vehicleType);

        return Response
                .ok()
                .entity(buildRegisterResponse(registration))
                .build();
    }

    @GET
    @Path("/report")
    public Response report(){

        var vehiclesParked = parkingApp.vehiclesParked();
        var checkoutLog = parkingApp.checkoutLog();

        return Response
                .ok()
                .entity(buildReportResponse(vehiclesParked, checkoutLog))
                .build();
    }

}
