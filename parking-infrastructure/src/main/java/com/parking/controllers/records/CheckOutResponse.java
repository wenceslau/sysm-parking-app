package com.parking.controllers.records;

public record CheckOutResponse(
        String plate,
        long duration,
        double amount) {
}
