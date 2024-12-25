package com.parking.infrastructure.resources;

import com.parking.application.ParkingApp;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static com.parking.infrastructure.resources.dto.Builder.*;

@ApplicationScoped
@Path("/parking")
public class ParkingResource {

    @Inject
    private ParkingApp parkingApp;

    @POST
    @Path("/open/{capacity}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response open(@PathParam("capacity") int capacity) {

        parkingApp.openParking(capacity);

        return Response
                .ok()
                .entity(buildOpenDTO(capacity))
                .build();

    }

    @POST
    @Path("/register/{licensePlate}/{vehicleType}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(@PathParam("licensePlate") String licensePlate, @PathParam("vehicleType") String vehicleType) {

        var registration = parkingApp.registerLicensePlate(licensePlate, vehicleType);

        return Response
                .ok()
                .entity(buildRegisterDTO(registration))
                .build();
    }

    @GET
    @Path("/report")
    @Produces(MediaType.APPLICATION_JSON)
    public Response report() {

        var vehiclesParked = parkingApp.vehiclesParked();
        var checkoutLog = parkingApp.checkoutLog();

        return Response
                .ok()
                .entity(buildReportDTO(vehiclesParked, checkoutLog))
                .build();
    }
}
