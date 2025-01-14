package com.parking.infrastructure.controllers.records;

import com.parking.domain.VehicleType;

import java.time.LocalDateTime;

public record CheckInResponse(
        String plate,
        VehicleType vehicleType,
        LocalDateTime checkIn) {
}
