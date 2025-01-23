package com.parking.domain.vehicles;

import com.parking.domain.Vehicle;
import com.parking.domain.VehicleType;

public class Motorcycle extends Vehicle {

    public Motorcycle(String licensePlate, double rate) {
        super(licensePlate, rate);
    }

    @Override
    public VehicleType getType() {
        return VehicleType.MOTORCYCLE;
    }
}
