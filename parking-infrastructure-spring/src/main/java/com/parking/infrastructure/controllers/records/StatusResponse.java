package com.parking.infrastructure.controllers.records;

public record StatusResponse(
        boolean isOpen,
        int occupation) {
}
