package com.parking.infrastructure.controllers.records;

import com.parking.domain.Registration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class Presentation {

    public static OpenResponse buildOpenResponse(int capacity) {
        return new OpenResponse(capacity, LocalDateTime.now());
    }

    public static RegisterResponse buildRegisterResponse(Registration registration) {

        var type = "check-in";
        Long duration = null;
        if (registration.getCheckOut() != null) {
            type = "check-out";
            duration = registration.getDuration().toMinutes();
        }
        String className = registration.getVehicle().getClass().getSimpleName().toUpperCase();

        return new RegisterResponse(
                type,
                registration.getVehicle().getLicensePlate(),
                className,
                registration.getVehicle().getRate(),
                registration.getCheckIn(),
                registration.getCheckOut(),
                duration,
                registration.getAmount());
    }

    public static ReportResponse buildReportResponse(List<Registration> vehiclesParked, List<Registration> checkoutReport) {

        var parkedVehicles = new ArrayList<ParkedResponse>();
        for (Registration registration : vehiclesParked) {
            var parked = new ParkedResponse(
                    registration.getVehicle().getLicensePlate(),
                    registration.getCheckIn());
            parkedVehicles.add(parked);
        }

        var checkoutVehicles = new ArrayList<CheckOutResponse>();
        for (Registration registration : checkoutReport) {
            var checkout = new CheckOutResponse(
                    registration.getVehicle().getLicensePlate(),
                    registration.getDuration().toMinutes(),
                    registration.getAmount());
            checkoutVehicles.add(checkout);
        }


        return new ReportResponse(parkedVehicles, checkoutVehicles);
    }

    public static List<RegistrationResponse> buildRegistrationsResponse(List<Registration> registrations) {
        var registrationResponses = new ArrayList<RegistrationResponse>();

        for (Registration registration : registrations) {
            var registrationResponse = new RegistrationResponse(
                    registration.getVehicle().getLicensePlate(),
                    registration.getVehicle().getClass().getSimpleName().toUpperCase(),
                    registration.getCheckIn(),
                    registration.getCheckOut(),
                    registration.getDuration() != null ? registration.getDuration().toMinutes() : null,
                    registration.getAmount());
            registrationResponses.add(registrationResponse);
        }

        return registrationResponses;
    }

}
