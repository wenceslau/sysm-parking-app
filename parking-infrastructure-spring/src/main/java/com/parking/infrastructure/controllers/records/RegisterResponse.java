package com.parking.infrastructure.controllers.records;

import java.time.LocalDateTime;

public record RegisterResponse(
        String type,
        String plate,
        String vehicleType,
        Double rate,
        LocalDateTime checkIn,
        LocalDateTime checkOut,
        Long duration,
        Double amountToPay) {
}
