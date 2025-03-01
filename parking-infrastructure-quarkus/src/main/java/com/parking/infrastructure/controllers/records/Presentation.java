package com.parking.infrastructure.controllers.records;

import com.parking.domain.Registration;
import com.parking.domain.VehicleType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class Presentation {

    public static StatusResponse buildStatusResponse(boolean isOpen, int capacity, int occupation) {
        return new StatusResponse(isOpen, capacity, occupation);
    }

    public static OpenResponse buildOpenResponse(int capacity) {
        return new OpenResponse(capacity, LocalDateTime.now());
    }

    public static RegisterResponse buildRegisterResponse(Registration registration, List<Registration> vehiclesParked,
                                                         List<Registration> checkoutLog) {

        var type = "checkIn";
        Long duration = null;
        if (registration.getCheckOut() != null) {
            type = "checkOut";
            duration = registration.getDuration().toMinutes();
        }
        String className = registration.getVehicle().getType().name();

        var checkInVehicles = buildCheckInResponse(vehiclesParked);
        var checkOutVehicles = buildCheckOutResponse(checkoutLog);

        return new RegisterResponse(
                type,
                registration.getVehicle().getLicensePlate(),
                className,
                registration.getVehicle().getRate(),
                registration.getCheckIn(),
                registration.getCheckOut(),
                duration,
                registration.getAmount(),
                checkInVehicles,
                checkOutVehicles);
    }

    public static List<CheckInResponse> buildCheckInResponse(List<Registration> vehiclesParked) {
        var checkInResponses = new ArrayList<CheckInResponse>();

        for (Registration registration : vehiclesParked) {
            var checkInResponse = new CheckInResponse(
                    registration.getVehicle().getLicensePlate(),
                    VehicleType.converter(registration.getVehicle().getType().name()),
                    registration.getCheckIn());
            checkInResponses.add(checkInResponse);
        }

        return checkInResponses;
    }

    public static List<CheckOutResponse> buildCheckOutResponse(List<Registration> checkoutLog) {
        var checkOutResponses = new ArrayList<CheckOutResponse>();

        for (Registration registration : checkoutLog) {
            var checkOutResponse = new CheckOutResponse(
                    registration.getVehicle().getLicensePlate(),
                    registration.getDuration().toMinutes(),
                    registration.getAmount());
            checkOutResponses.add(checkOutResponse);
        }

        return checkOutResponses;
    }

    public static List<RegistrationResponse> buildRegistrationsResponse(List<Registration> registrations) {
        var registrationResponses = new ArrayList<RegistrationResponse>();

        for (Registration registration : registrations) {
            var registrationResponse = new RegistrationResponse(
                    registration.getVehicle().getLicensePlate(),
                    registration.getVehicle().getType().name(),
                    registration.getCheckIn(),
                    registration.getCheckOut(),
                    registration.getDuration() != null ? registration.getDuration().toMinutes() : null,
                    registration.getVehicle().getRate(),
                    registration.getAmount());
            registrationResponses.add(registrationResponse);
        }

        return registrationResponses;
    }
}
