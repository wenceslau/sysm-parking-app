package com.parking.infrastructure.resources.dto;

import java.time.LocalDateTime;

public class RegistrationDTO{
    private String plate;
    private String vehicleType;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Long duration;
    private Double price;

    public String getPlate() {
        return plate;
    }

    public RegistrationDTO setPlate(String plate) {
        this.plate = plate;
        return this;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public RegistrationDTO setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public RegistrationDTO setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
        return this;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public RegistrationDTO setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
        return this;
    }

    public Long getDuration() {
        return duration;
    }

    public RegistrationDTO setDuration(Long duration) {
        this.duration = duration;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public RegistrationDTO setPrice(Double price) {
        this.price = price;
        return this;
    }
}
