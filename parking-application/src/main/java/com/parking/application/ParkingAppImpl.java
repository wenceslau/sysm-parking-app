package com.parking.application;

import com.parking.domain.*;

import java.time.LocalDate;
import java.util.List;

public class ParkingAppImpl implements ParkingApp {

    private final ParkingGateway parkingGateway;
    private Parking parking;

    public ParkingAppImpl(ParkingGateway parkingGateway) {
        this.parkingGateway = parkingGateway;
    }

    public boolean isOpen() {
       return parking != null && parking.isOpen();
    }

    @Override
    public int capacity() {
        return parking.getCapacity();
    }

    public void openParking(int capacity) {
        if (parking == null) {
            var optional = parkingGateway.findParkingByCurrentDay();
            parking = optional.orElseGet(Parking::new);
        }
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

    public List<Registration> findAllByCheckinDay(LocalDate date) {
        return parkingGateway.findAllByCheckInDay(date);
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
