package com.parking.infrastructure.controllers.records;

public record CheckOutResponse(
        String plate,
        long duration,
        double amount) {
}
