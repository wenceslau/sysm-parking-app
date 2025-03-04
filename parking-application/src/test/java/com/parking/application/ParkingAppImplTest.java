package com.parking.application;

import com.parking.domain.Parking;
import com.parking.domain.ParkingGateway;
import com.parking.domain.Registration;
import com.parking.domain.VehicleType;
import com.parking.domain.vehicles.Car;
import com.parking.domain.vehicles.Motorcycle;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkingAppImplTest {


    @Test
    void isOpenShouldReturnFalseForNewlyCreatedParkingAppImpl() {
        // Arrange
        ParkingGateway mockGateway = mock(ParkingGateway.class);
        when(mockGateway.loadAllByCurrentDay()).thenReturn(Collections.emptyList());

        // Act
        ParkingAppImpl parkingApp = new ParkingAppImpl(mockGateway);
        boolean isOpen = parkingApp.isOpen();

        // Assert
        assertFalse(isOpen);
    }

    @Test
    void shouldReturnCorrectCapacityWhenCapacityMethodIsCalled() {
        // Arrange
        ParkingGateway mockParkingGateway = mock(ParkingGateway.class);
        when(mockParkingGateway.loadAllByCurrentDay()).thenReturn(Collections.emptyList());
        ParkingAppImpl parkingApp = new ParkingAppImpl(mockParkingGateway);
        int expectedCapacity = 10;
        parkingApp.openParking(expectedCapacity);

        // Act
        int actualCapacity = parkingApp.capacity();

        // Assert
        assertEquals(expectedCapacity, actualCapacity);
    }

    @Test
    void shouldSuccessfullyOpenParkingWithSpecifiedCapacity() {
        // Arrange
        ParkingGateway mockParkingGateway = mock(ParkingGateway.class);
        when(mockParkingGateway.loadAllByCurrentDay()).thenReturn(Collections.emptyList());
        ParkingAppImpl parkingApp = new ParkingAppImpl(mockParkingGateway);
        int expectedCapacity = 50;

        // Act
        parkingApp.openParking(expectedCapacity);

        // Assert
        assertTrue(parkingApp.isOpen());
        assertEquals(expectedCapacity, parkingApp.capacity());
    }

    @Test
    void shouldThrowExceptionWhenOpeningParkingWithNegativeCapacity() {
        // Arrange
        ParkingGateway mockParkingGateway = mock(ParkingGateway.class);
        when(mockParkingGateway.loadAllByCurrentDay()).thenReturn(Collections.emptyList());
        ParkingAppImpl parkingApp = new ParkingAppImpl(mockParkingGateway);
        int negativeCapacity = -10;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> parkingApp.openParking(negativeCapacity));
    }

    @Test
    void shouldCorrectlyRegisterLicensePlateForCarAndSaveRegistration() {
        // Arrange
        ParkingGateway mockParkingGateway = mock(ParkingGateway.class);
        when(mockParkingGateway.loadAllByCurrentDay()).thenReturn(Collections.emptyList());
        when(mockParkingGateway.getRateByType(VehicleType.CAR)).thenReturn(5.0);
        ParkingAppImpl parkingApp = new ParkingAppImpl(mockParkingGateway);
        parkingApp.openParking(10);
        String licensePlate = "ABC-1234";
        String vehicleType = "CAR";

        // Act
        Registration result = parkingApp.registerLicensePlate(licensePlate, vehicleType);

        // Assert
        assertNotNull(result);
        assertEquals(licensePlate, result.getVehicle().getLicensePlate());
        assertEquals(VehicleType.CAR, result.getVehicle().getType());
        verify(mockParkingGateway).save(result);
    }


    @Test
    void shouldThrowExceptionWhenRegisteringInvalidVehicleType() {
        // Arrange
        ParkingGateway mockParkingGateway = mock(ParkingGateway.class);
        when(mockParkingGateway.loadAllByCurrentDay()).thenReturn(Collections.emptyList());
        ParkingAppImpl parkingApp = new ParkingAppImpl(mockParkingGateway);
        parkingApp.openParking(10);
        String licensePlate = "ABC-1234";
        String invalidVehicleType = "InvalidType";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> parkingApp.registerLicensePlate(licensePlate, invalidVehicleType));
    }

    @Test
    void shouldReturnEmptyListWhenFindAllByCheckinDayCalledWithNoCheckIns() {
        // Arrange
        ParkingGateway mockParkingGateway = mock(ParkingGateway.class);
        LocalDate dateWithNoCheckIns = LocalDate.now();
        when(mockParkingGateway.findAllByCheckInDay(dateWithNoCheckIns)).thenReturn(Collections.emptyList());
        ParkingAppImpl parkingApp = new ParkingAppImpl(mockParkingGateway);

        // Act
        List<Registration> result = parkingApp.findAllByCheckinDay(dateWithNoCheckIns);

        // Assert
        assertTrue(result.isEmpty());
        verify(mockParkingGateway).findAllByCheckInDay(dateWithNoCheckIns);
    }

    @Test
    void shouldReturnListOfVehiclesCurrentlyParkedWhenVehiclesParkedMethodIsCalled() {
        // Arrange
        var vehicle = new Car("ABC-1234", 10.0);
        var vehicle3 = new Motorcycle("ABC-1236", 4.0);

        ParkingGateway mockParkingGateway = mock(ParkingGateway.class);
        List<Registration> allRegistrations = Arrays.asList(
                new Registration(vehicle, LocalDateTime.now()),
                new Registration(vehicle3, LocalDateTime.now())
        );
        Parking parking = new Parking("123", LocalDate.now(), allRegistrations);
        when(mockParkingGateway.findParkingByCurrentDay()).thenReturn(Optional.of(parking));

        ParkingAppImpl parkingApp = new ParkingAppImpl(mockParkingGateway);
        parkingApp.openParking(10);

        // Act
        List<Registration> parkedVehicles = parkingApp.vehiclesParked();

        // Assert
        assertEquals(2, parkedVehicles.size());
        assertTrue(parkedVehicles.stream().allMatch(r -> r.getCheckOut() == null));
        assertTrue(parkedVehicles.stream().anyMatch(r -> r.getVehicle().getLicensePlate().equals("ABC-1234")));
        assertTrue(parkedVehicles.stream().anyMatch(r -> r.getVehicle().getLicensePlate().equals("ABC-1236")));
    }

    @Test
    void shouldReturnEmptyListWhenCheckoutLogIsCalledAndNoVehiclesHaveCheckedOut() {
        // Arrange
        ParkingGateway mockParkingGateway = mock(ParkingGateway.class);
        List<Registration> mockRegistrations = Arrays.asList(
                new Registration(new Car("ABC-1234", 10.0), LocalDateTime.now()),
                new Registration(new Motorcycle("XYZ-9789", 5.0), LocalDateTime.now())
        );
        when(mockParkingGateway.loadAllByCurrentDay()).thenReturn(mockRegistrations);
        ParkingAppImpl parkingApp = new ParkingAppImpl(mockParkingGateway);
        parkingApp.openParking(10);
        // Act
        List<Registration> checkoutLog = parkingApp.checkoutLog();

        // Assert
        assertTrue(checkoutLog.isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenRegisteringLicensePlateInFullParking() {
        // Arrange
        ParkingGateway mockParkingGateway = mock(ParkingGateway.class);
        when(mockParkingGateway.loadAllByCurrentDay()).thenReturn(Collections.emptyList());
        when(mockParkingGateway.getRateByType(any())).thenReturn(10.0);
        ParkingAppImpl parkingApp = new ParkingAppImpl(mockParkingGateway);
        parkingApp.openParking(1);
        parkingApp.registerLicensePlate("ABC-1123", "CAR");

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> parkingApp.registerLicensePlate("XYZ-1789", "CAR"));
    }
}
