package com.parking.controllers.records;

import java.time.LocalDateTime;

public record ParkedResponse(
        String plate,
        LocalDateTime checkInTime) {
}
