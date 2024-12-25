package com.parking;

public abstract class Vehicle {

    private final String licensePlate;
    private final double rate;

    private double additionalHourValue = 6.0;

    public Vehicle(String licensePlate, double rate) {
        this.licensePlate = licensePlate;
        this.rate = rate;
        validate();
    }

    public void setAdditionalHourValue(double additionalHourValue) {
        this.additionalHourValue = additionalHourValue;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public double getRate() {
        return rate;
    }

    public double getAdditionalHourValue() {
        return additionalHourValue;
    }

    protected double amountToPay(long minutes) {
        if (minutes <= 5) {
            return 0;
        } else if (minutes <= 60) {
            return getRate();
        } else {
            long extraHours = (minutes - 60) / 60;
            double extraMinutes = (minutes - 60) % 60;

            return getRate() + (extraHours * additionalHourValue) + (extraMinutes > 0 ? additionalHourValue : 0);
        }
    }

    private void validate(){
        if (licensePlate == null || licensePlate.length() != 8) {
            throw new IllegalArgumentException("License plate is invalid. Format must be: XXX-0000");
        }
        if (rate <= 0) {
            throw new IllegalArgumentException("Rate must be greater than zero");
        }
    }

}
