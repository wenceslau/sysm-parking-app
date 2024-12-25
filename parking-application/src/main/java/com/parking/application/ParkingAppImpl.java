package com.parking.application;

import com.parking.domain.*;

import java.util.List;

public class ParkingAppImpl implements ParkingApp {

    private final Parking parking;
    private final ParkingGateway parkingGateway;

    public ParkingAppImpl(ParkingGateway parkingGateway) {
        this.parkingGateway = parkingGateway;
        var registrations = parkingGateway.loadAllByCurrentDay();
        parking = new Parking(registrations);
    }

    public void openParking(int capacity) {
        parking.openParking(capacity);
    }

    public Registration registerLicensePlate(String licensePlate, String vehicleType) {

        // Convert the string to the VehicleType enum
        var vehicleTypeEnum = VehicleType.converter(vehicleType);

        double rate = parkingGateway.getRateByType(vehicleTypeEnum);

        Vehicle vehicle = vehicleTypeEnum.getVehicle(licensePlate, rate);

        var vehicleRegistration = this.parking.registerPlate(vehicle);

        parkingGateway.save(vehicleRegistration);

        return vehicleRegistration;
    }

    public List<Registration> vehiclesParked() {
        return parking.getRegistrations().stream()
                .filter(r -> r.getCheckOut() == null)
                .toList();
    }

    public List<Registration> checkoutLog() {
        return parking.getRegistrations().stream()
                .filter(r -> r.getCheckOut() != null)
                .toList();
    }

}
