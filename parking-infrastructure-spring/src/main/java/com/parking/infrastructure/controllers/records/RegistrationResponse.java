package com.parking.infrastructure.controllers.records;

import java.time.LocalDateTime;

public record RegistrationResponse(
        String plate,
        String vehicleType,
        LocalDateTime checkIn,
        LocalDateTime checkOut,
        Long duration,
        Double price) {
}
