package com.parking.infrastructure.resources;

import com.parking.application.ParkingApp;
import com.parking.infrastructure.configuration.CacheConfig;
import com.parking.infrastructure.resources.dto.RegisterDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;

import static com.parking.infrastructure.resources.dto.Builder.*;

@ApplicationScoped
@Path("/parking")
public class ParkingResource {

    @Inject
    private ParkingApp parkingApp;

    @Inject
    private CacheConfig cacheConfig;

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
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(@Valid RegisterDTO register) {

        var registration = parkingApp.registerLicensePlate(
                register.getLicensePlate(),
                register.getVehicleType());

        cacheConfig.removeCache("reportByDate", LocalDate.now());
        System.out.println("Cache cleared");

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

    @GET
    @Path("/report/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response report(@PathParam("date") String date) {
        LocalDate localDate = LocalDate.parse(date);

        var cache = cacheConfig.getCache("reportByDate", date);
        if (cache == null) {
            System.out.println("Cache miss, fetching from database");
            var registrations = parkingApp.findAllByCheckinDay(localDate);
            cache = buildRegistrationsDTO(registrations);
            cacheConfig.addCache("reportByDate", cache, date);
        }

        return Response
                .ok()
                .entity(cache)
                .build();
    }


}
