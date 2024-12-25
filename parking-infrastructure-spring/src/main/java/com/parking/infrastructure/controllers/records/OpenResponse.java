package com.parking.infrastructure.controllers.records;


import java.time.LocalDateTime;

public record OpenResponse(
        int capacity,
        LocalDateTime openedAt) {
}
