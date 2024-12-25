package com.parking.resources.dto;

import java.time.Duration;
import java.time.LocalDateTime;

public class RegisterDTO {
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

    public RegisterDTO setType(String type) {
        this.type = type;
        return this;
    }

    public String getPlate() {
        return plate;
    }

    public RegisterDTO setPlate(String plate) {
        this.plate = plate;
        return this;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public RegisterDTO setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public double getRate() {
        return rate;
    }

    public RegisterDTO setRate(double rate) {
        this.rate = rate;
        return this;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public RegisterDTO setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
        return this;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public RegisterDTO setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
        return this;
    }

    public Duration getDuration() {
        return duration;
    }

    public RegisterDTO setDuration(Duration duration) {
        this.duration = duration;
        return this;
    }

    public double getAmountToPay() {
        return amountToPay;
    }

    public RegisterDTO setAmountToPay(double amountToPay) {
        this.amountToPay = amountToPay;
        return this;
    }
}
