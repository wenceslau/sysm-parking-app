package com.parking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Parking {

    private final List<Registration> registrations;

    private Integer capacity;

    public Parking() {
        this.registrations = new ArrayList<>();
    }

    public Parking(List<Registration> registrations) {
        this.registrations = new ArrayList<>(registrations);
    }

    public void openParking(int capacity) {

        if (isOpened()) {
            throw new IllegalStateException("Parking lot is already opened");
        }

        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero");
        }

        this.capacity = capacity;
    }

    public Registration registerPlate(Vehicle vehicle) {

        if (!isOpened()) {
            throw new IllegalStateException("Parking lot is closed");
        }

        if (vehicle == null){
            throw new IllegalArgumentException("Vehicle is required");
        }

        var optional = searchRegistration(vehicle);

        return optional
                .map(this::checkOutVehicle)
                .orElseGet(() -> checkInVehicle(vehicle));
    }

    private Optional<Registration> searchRegistration(Vehicle vehicle) {
        return registrations.stream()
                .filter(r -> r.getVehicle().getLicensePlate().equals(vehicle.getLicensePlate()))
                .filter(r -> r.getCheckOut() == null)
                .findFirst();
    }

    private Registration checkInVehicle(Vehicle vehicle) {
        int count = registrations.stream()
                .filter(r -> r.getCheckOut() == null)
                .mapToInt(r -> 1).sum();

        if (count == capacity) {
            throw new IllegalStateException("Parking lot is full");
        }

        var registration = new Registration(vehicle, LocalDateTime.now());
        registrations.add(registration);
        return registration;
    }

    private Registration checkOutVehicle(Registration registration) {
        registration.checkOut(LocalDateTime.now());
        return registration;
    }

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isOpened() {
        return capacity != null;
    }
}
