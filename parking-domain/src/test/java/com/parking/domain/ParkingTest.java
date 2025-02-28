package com.parking.domain;

import com.parking.domain.vehicles.Car;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParkingTest {

    @Test
    void shouldOpenParkingWithValidCapacity() {
        Parking parking = new Parking();
        parking.openParking(10);
        assertTrue(parking.isOpen());
        assertEquals(10, parking.getCapacity());
    }

    @Test
    void shouldThrowExceptionWhenOpeningAlreadyOpenedParking() {
        Parking parking = new Parking();
        parking.openParking(10);
        assertThrows(IllegalStateException.class, () -> parking.openParking(10));
    }

    @Test
    void shouldThrowExceptionWhenOpeningParkingWithZeroCapacity() {
        Parking parking = new Parking();
        assertThrows(IllegalArgumentException.class, () -> parking.openParking(0));
    }

    @Test
    void shouldThrowExceptionWhenOpeningParkingWithNegativeCapacity() {
        Parking parking = new Parking();
        assertThrows(IllegalArgumentException.class, () -> parking.openParking(-1));
    }

    @Test
    void shouldRegisterVehicleWhenParkingIsOpen() {
        Parking parking = new Parking();
        parking.openParking(10);
        Vehicle vehicle = new Car("ABC-1234", 5.5);
        Registration registration = parking.registerPlate(vehicle);
        assertNotNull(registration);
        assertEquals(vehicle, registration.getVehicle());
        assertNotNull(registration.getCheckIn());
        assertNull(registration.getCheckOut());
    }

    @Test
    void shouldThrowExceptionWhenRegisteringVehicleInClosedParking() {
        Parking parking = new Parking();
        Vehicle vehicle = new Car("ABC-1234", 5.5);
        assertThrows(IllegalStateException.class, () -> parking.registerPlate(vehicle));
    }

    @Test
    void shouldThrowExceptionWhenRegisteringNullVehicle() {
        Parking parking = new Parking();
        parking.openParking(10);
        assertThrows(IllegalArgumentException.class, () -> parking.registerPlate(null));
    }

    @Test
    void shouldCheckOutVehicleWhenRegisteringExistingPlate() {
        Parking parking = new Parking();
        parking.openParking(10);
        Vehicle vehicle = new Car("ABC-1234", 5.5);
        parking.registerPlate(vehicle);
        Registration registration = parking.registerPlate(vehicle);
        assertNotNull(registration);
        assertNotNull(registration.getCheckIn());
        assertNotNull(registration.getCheckOut());
    }

    @Test
    void shouldThrowExceptionWhenParkingIsFull() {
        Parking parking = new Parking();
        parking.openParking(1);
        Vehicle vehicle1 = new Car("ABC-1234", 5.5);
        parking.registerPlate(vehicle1);
        Vehicle vehicle2 = new Car("DEF-5678", 5.5);
        assertThrows(IllegalStateException.class, () -> parking.registerPlate(vehicle2));
    }

    @Test
    void shouldReturnRegistrations() {
        Parking parking = new Parking();
        parking.openParking(10);
        Vehicle vehicle = new Car("ABC-1234", 5.5);
        parking.registerPlate(vehicle);
        List<Registration> registrations = parking.getRegistrations();
        assertEquals(1, registrations.size());
    }

    @Test
    void shouldCalculateCorrectAmountToPay() {
        Parking parking = new Parking();
        parking.openParking(10);

        Vehicle vehicle = new Car("ABC-1234", 5.5);
        vehicle.setAdditionalHourValue(6.0);

        LocalDateTime checkInTime = LocalDateTime.now();
        Registration registration = new Registration(vehicle, checkInTime);

        LocalDateTime checkOutTime = checkInTime.plusMinutes(121); // 2 hours and 1 minute
        registration.checkOut(checkOutTime);

        long minutesParked = ChronoUnit.MINUTES.between(checkInTime, checkOutTime);
        double expectedAmount = vehicle.amountToPay(minutesParked); // Using Vehicle's amountToPay method

        assertEquals(17.5, expectedAmount); // Expected amount for 2 hours and 1 minute
    }

    @Test
    void shouldCreateParkingWithExistingRegistrations() {
        // Arrange
        List<Registration> existingRegistrations = new ArrayList<>();
        Vehicle vehicle1 = new Car("ABC-1234", 5.5);
        Registration registration1 = new Registration(vehicle1, LocalDateTime.now().minusHours(2));
        existingRegistrations.add(registration1);
        String id = "123";
        LocalDate referenceDate = LocalDate.now();

        // Act
        Parking parking = new Parking(id, referenceDate, existingRegistrations);

        // Assert
        assertEquals(1, parking.getRegistrations().size());
        assertEquals(registration1, parking.getRegistrations().get(0));
    }

    @Test
    void shouldNotModifyOriginalRegistrationListWhenCreatingParking() {
        // Arrange
        List<Registration> originalRegistrations = new ArrayList<>();
        Vehicle vehicle1 = new Car("ABC-1234", 5.5);
        Registration registration1 = new Registration(vehicle1, LocalDateTime.now().minusHours(2));
        originalRegistrations.add(registration1);
        String id = "123";
        LocalDate referenceDate = LocalDate.now();

        // Act
        Parking parking = new Parking(id, referenceDate, originalRegistrations);
        parking.getRegistrations().add(new Registration(new Car("DEF-5678", 6.0), LocalDateTime.now()));

        // Assert
        assertEquals(1, originalRegistrations.size()); // Original list should remain unchanged
        assertEquals(2, parking.getRegistrations().size()); // Parking's list should contain the added registration

    }
}
