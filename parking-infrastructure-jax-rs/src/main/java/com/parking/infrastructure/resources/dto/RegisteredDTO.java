package com.parking.infrastructure.resources.dto;

import java.time.Duration;
import java.time.LocalDateTime;

public class RegisteredDTO {
    private String type;
    private String plate;
    private String vehicleType;
    private double rate;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Duration duration;
    private Double amountToPay;

    public String getType() {
        return type;
    }

    public RegisteredDTO setType(String type) {
        this.type = type;
        return this;
    }

    public String getPlate() {
        return plate;
    }

    public RegisteredDTO setPlate(String plate) {
        this.plate = plate;
        return this;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public RegisteredDTO setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public double getRate() {
        return rate;
    }

    public RegisteredDTO setRate(double rate) {
        this.rate = rate;
        return this;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public RegisteredDTO setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
        return this;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public RegisteredDTO setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
        return this;
    }

    public Duration getDuration() {
        return duration;
    }

    public RegisteredDTO setDuration(Duration duration) {
        this.duration = duration;
        return this;
    }

    public Double getAmountToPay() {
        return amountToPay;
    }

    public RegisteredDTO setAmountToPay(Double amountToPay) {
        this.amountToPay = amountToPay;
        return this;
    }
}
