package com.parking.infrastructure.controllers.records;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank String vehicleType,
        @NotBlank String licensePlate) {
}
