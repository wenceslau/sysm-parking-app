package com.parking.resources.dto;

public class CheckOutDTO {
    private String plate;
    private long duration;
    private double amount;

    public String getPlate() {
        return plate;
    }

    public CheckOutDTO setPlate(String plate) {
        this.plate = plate;
        return this;
    }

    public long getDuration() {
        return duration;
    }

    public CheckOutDTO setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public CheckOutDTO setAmount(double amount) {
        this.amount = amount;
        return this;
    }
}
