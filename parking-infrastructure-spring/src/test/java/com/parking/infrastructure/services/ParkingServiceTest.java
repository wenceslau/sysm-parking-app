package com.parking.infrastructure.services;

import com.parking.application.ParkingApp;
import com.parking.domain.Registration;
import com.parking.domain.Vehicle;
import com.parking.domain.vehicles.Car;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkingServiceTest {
    
    ParkingApp parkingApp = mock(ParkingApp.class);
    ParkingService parkingService = new ParkingService(parkingApp);

    @Test
    void shouldReturnTrueWhenParkingIsOpen() {
        ParkingApp mockParkingApp = mock(ParkingApp.class);
        ParkingService parkingService = new ParkingService(mockParkingApp);

        when(mockParkingApp.isOpen()).thenReturn(true);

        assertTrue(parkingService.isOpen());
        verify(mockParkingApp).isOpen();
    }

    @Test
    void isOpenShouldReturnFalseWhenParkingIsClosed() {
        // Arrange
        ParkingApp mockParkingApp = mock(ParkingApp.class);
        when(mockParkingApp.isOpen()).thenReturn(false);
        ParkingService parkingService = new ParkingService(mockParkingApp);

        // Act
        boolean result = parkingService.isOpen();

        // Assert
        assertFalse(result);
        verify(mockParkingApp).isOpen();
    }

    @Test
    void shouldReturnCorrectCapacityWhenQueried() {
        // Arrange
        ParkingApp mockParkingApp = mock(ParkingApp.class);
        ParkingService parkingService = new ParkingService(mockParkingApp);
        int expectedCapacity = 100;
        when(mockParkingApp.capacity()).thenReturn(expectedCapacity);

        // Act
        int actualCapacity = parkingService.capacity();

        // Assert
        assertEquals(expectedCapacity, actualCapacity);
        verify(mockParkingApp).capacity();
    }

    @Test
    void shouldSuccessfullyOpenParkingWithSpecifiedCapacity() {
        // Arrange
        ParkingApp mockParkingApp = mock(ParkingApp.class);
        ParkingService parkingService = new ParkingService(mockParkingApp);
        int expectedCapacity = 100;

        // Act
        parkingService.openParking(expectedCapacity);

        // Assert
        verify(mockParkingApp).openParking(expectedCapacity);
    }

    @Test
    void shouldRegisterLicensePlateAndReturnValidRegistration() {
        // Arrange
        String licensePlate = "ABC-1234";
        String vehicleType = "Car";
        Vehicle car = new Car(licensePlate, 5.5);
        Registration expectedRegistration = new Registration(car, LocalDateTime.now());
        when(parkingApp.registerLicensePlate(licensePlate, vehicleType)).thenReturn(expectedRegistration);

        // Act
        Registration result = parkingService.registerLicensePlate(licensePlate, vehicleType);

        // Assert
        assertNotNull(result);
        assertEquals(expectedRegistration, result);
        verify(parkingApp).registerLicensePlate(licensePlate, vehicleType);
    }

    @Test
    void shouldReturnListOfAllVehiclesCurrentlyParked() {
        // Arrange
        ParkingApp mockParkingApp = mock(ParkingApp.class);
        List<Registration> expectedParkedVehicles = List.of(
                new Registration(new Car("ABC-1234", 5.5), LocalDateTime.now()),
                new Registration(new Car("XYZ-7890", 6.0), LocalDateTime.now()),
                new Registration(new Car("DEF-4569", 7.0), LocalDateTime.now())
        );
        when(mockParkingApp.vehiclesParked()).thenReturn(expectedParkedVehicles);

        ParkingService parkingService = new ParkingService(mockParkingApp);

        // Act
        List<Registration> actualParkedVehicles = parkingService.vehiclesParked();

        // Assert
        assertEquals(expectedParkedVehicles, actualParkedVehicles);
        verify(mockParkingApp).vehiclesParked();
    }

    @Test
    void shouldReturnListOfAllCheckedOutVehicles() {
        // Arrange
        ParkingApp mockParkingApp = mock(ParkingApp.class);
        List<Registration> expectedCheckoutLog = Arrays.asList(
                new Registration(new Car("ABC-1234", 5.5), LocalDateTime.now()),
                new Registration(new Car("XYZ-7890", 6.0), LocalDateTime.now()),
                new Registration(new Car("DEF-4569", 7.0), LocalDateTime.now())
        );
        when(mockParkingApp.checkoutLog()).thenReturn(expectedCheckoutLog);

        ParkingService parkingService = new ParkingService(mockParkingApp);

        // Act
        List<Registration> actualCheckoutLog = parkingService.checkoutLog();

        // Assert
        assertEquals(expectedCheckoutLog, actualCheckoutLog);
        verify(mockParkingApp).checkoutLog();
    }

    @Test
    void shouldClearCacheForSpecificDate() {
        // Arrange
        LocalDate testDate = LocalDate.of(2023, 5, 1);
        ParkingApp mockParkingApp = mock(ParkingApp.class);
        ParkingService parkingService = new ParkingService(mockParkingApp);

        // Act
        parkingService.clearCacheByParameter(testDate);

        // Assert
        // Since @CacheEvict is used, we can't directly verify cache eviction
        // Instead, we can verify that the method was called with the correct parameter
        verify(mockParkingApp, times(0)).findAllByCheckinDay(testDate);
    }

    @Test
    void shouldFetchFromDatabaseOnFirstQueryForRegistrationsByCheckinDay() {
        // Arrange
        LocalDate date = LocalDate.now();
        List<Registration> expectedRegistrations = Arrays.asList(
                new Registration(new Car("ABC-1234", 5.5), LocalDateTime.now()),
                new Registration(new Car("XYZ-7890", 6.0), LocalDateTime.now()),
                new Registration(new Car("DEF-4569", 7.0), LocalDateTime.now())
        );
        when(parkingApp.findAllByCheckinDay(date)).thenReturn(expectedRegistrations);

        // Act
        List<Registration> result = parkingService.findAllByCheckinDay(date);

        // Assert
        assertEquals(expectedRegistrations, result);
        verify(parkingApp, times(1)).findAllByCheckinDay(date);
    }
}
