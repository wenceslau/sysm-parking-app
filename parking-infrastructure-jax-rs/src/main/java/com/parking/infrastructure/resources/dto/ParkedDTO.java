package com.parking.infrastructure.resources.dto;

import java.time.LocalDateTime;

public class ParkedDTO {
    private String plate;
    private LocalDateTime checkIn;

    public String getPlate() {
        return plate;
    }

    public ParkedDTO setPlate(String plate) {
        this.plate = plate;
        return this;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public ParkedDTO setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
        return this;
    }
}
