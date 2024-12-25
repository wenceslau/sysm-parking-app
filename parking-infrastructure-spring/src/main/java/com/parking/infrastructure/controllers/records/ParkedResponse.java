package com.parking.infrastructure.controllers.records;

import java.time.LocalDateTime;

public record ParkedResponse(
        String plate,
        LocalDateTime checkIn) {
}
