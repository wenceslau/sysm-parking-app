package com.parking.infrastructure.resources.dto;

import com.parking.domain.Registration;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class Builder {

    public static OpenDTO buildOpenDTO(int capacity) {
        return new OpenDTO()
                .setCapacity(capacity)
                .setCreatedAt(LocalDateTime.now());
    }

    public static RegisteredDTO buildRegisterDTO(Registration registration) {

        var type = "check-in";
        Duration duration = null;
        if (registration.getCheckOut() != null) {
            type = "check-out";
            duration = Duration.between(registration.getCheckIn(), registration.getCheckOut());
        }
        String className = registration.getVehicle().getClass().getSimpleName().toUpperCase();

        return new RegisteredDTO()
                .setType(type)
                .setPlate(registration.getVehicle().getLicensePlate())
                .setVehicleType(className)
                .setRate(registration.getVehicle().getRate())
                .setCheckIn(registration.getCheckIn())
                .setCheckOut(registration.getCheckOut())
                .setDuration(duration)
                .setAmountToPay(registration.getAmount());
    }

    public static ReportDTO buildReportDTO(List<Registration> vehiclesParked, List<Registration> checkoutReport) {

        var parkedVehicles = new ArrayList<ParkedDTO>();
        for (Registration registration : vehiclesParked) {
            var parked = new ParkedDTO()
                    .setPlate(registration.getVehicle().getLicensePlate())
                    .setCheckIn(registration.getCheckIn());
            parkedVehicles.add(parked);
        }

        var checkoutVehicles = new ArrayList<CheckOutDTO>();
        for (Registration registration : checkoutReport) {
            var checkout = new CheckOutDTO()
                    .setPlate(registration.getVehicle().getLicensePlate())
                    .setDuration(registration.getDuration().toMinutes())
                    .setAmount(registration.getAmount());
            checkoutVehicles.add(checkout);
        }

        return new ReportDTO()
                .setParkedVehicles(parkedVehicles)
                .setCheckoutVehicles(checkoutVehicles);
    }

    public static List<RegistrationDTO> buildRegistrationsDTO(List<Registration> registrations) {
        var registrationDTOs = new ArrayList<RegistrationDTO>();

        for (Registration registration : registrations) {

            var duration = registration.getDuration() != null ? registration.getDuration().toMinutes() : null;
            var registrationDTO = new RegistrationDTO()
                    .setPlate(registration.getVehicle().getLicensePlate())
                    .setVehicleType(registration.getVehicle().getClass().getSimpleName().toUpperCase())
                    .setCheckIn(registration.getCheckIn())
                    .setCheckOut(registration.getCheckOut())
                    .setDuration(duration)
                    .setPrice(registration.getAmount());
            registrationDTOs.add(registrationDTO);
        }

        return registrationDTOs;
    }


}
