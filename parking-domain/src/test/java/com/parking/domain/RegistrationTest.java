package com.parking.domain;

import com.parking.domain.vehicles.Car;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class RegistrationTest {


    @Test
    void shouldCreateValidRegistrationObject() {
        // Arrange
        Vehicle mockVehicle = mock(Vehicle.class);
        LocalDateTime checkIn = LocalDateTime.now();

        // Act
        Registration registration = new Registration(mockVehicle, checkIn);

        // Assert
        assertNotNull(registration.getId());
        assertEquals(36, registration.getId().length()); // UUID length
        assertEquals(mockVehicle, registration.getVehicle());
        assertEquals(checkIn, registration.getCheckIn());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenCreatingRegistrationWithNullVehicle() {
        LocalDateTime checkIn = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class, () -> {
            new Registration(null, checkIn);
        }, "Vehicle is required");
    }

    @Test
    void shouldThrowExceptionWhenCheckInIsNull() {
        Vehicle vehicle = new Car("ABC-1234", 10.0);
        LocalDateTime checkIn = null;

        assertThrows(IllegalArgumentException.class, () -> new Registration(vehicle, checkIn));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenCheckingOutWithNullCheckOutDate() {
        // Arrange
        Vehicle vehicle = new Car("ABC-1234", 10.0);
        LocalDateTime checkIn = LocalDateTime.now();
        Registration registration = new Registration(vehicle, checkIn);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> registration.checkOut(null));
    }

    @Test
    public void shouldThrowExceptionWhenCheckOutBeforeCheckIn() {
        Vehicle vehicle = new Car("ABC-1234", 10.0);
        LocalDateTime checkIn = LocalDateTime.now();
        Registration registration = new Registration(vehicle, checkIn);

        LocalDateTime invalidCheckOut = checkIn.minusHours(1);

        assertThrows(IllegalArgumentException.class, () -> registration.checkOut(invalidCheckOut));
    }

    @Test
    void shouldCorrectlyCalculateDurationAndAmountWhenCheckingOut() {
        LocalDateTime checkIn = LocalDateTime.of(2023, 5, 1, 10, 0);
        LocalDateTime checkOut = LocalDateTime.of(2023, 5, 1, 12, 30);
        Vehicle vehicle = new Car("ABC-1234", 2.0);
        Registration registration = new Registration(vehicle, checkIn);

        registration.checkOut(checkOut);

        // extra hours = 6.0
        assertEquals(Duration.ofMinutes(150), registration.getDuration());
        assertEquals(14.0, registration.getAmount());
    }

    @Test
    public void shouldReturnCorrectValuesForAllGetterMethods() {
        // Arrange
        Vehicle vehicle = new Car("ABC-1234", 10.0);
        LocalDateTime checkIn = LocalDateTime.now();
        LocalDateTime checkOut = checkIn.plusHours(2);
        Registration registration = new Registration(vehicle, checkIn);
        registration.checkOut(checkOut);

        // Act & Assert
        assertNotNull(registration.getId());
        assertEquals(vehicle, registration.getVehicle());
        assertEquals(checkIn, registration.getCheckIn());
        assertEquals(checkOut, registration.getCheckOut());
        assertEquals(Duration.between(checkIn, checkOut), registration.getDuration());
        assertEquals(vehicle.amountToPay(Duration.between(checkIn, checkOut).toMinutes()), registration.getAmount());
    }

    @Test
    public void shouldCreateValidRegistrationUsingFromMethod() {
        // Arrange
        String id = "123";
        String vehicleType = "CAR";
        String licensePlate = "ABC-1235";
        Double rate = 2.5;
        LocalDateTime checkIn = LocalDateTime.now();
        LocalDateTime checkOut = checkIn.plusHours(2);
        Duration duration = Duration.between(checkIn, checkOut);
        Double amount = 5.0;

        // Act
        Registration registration = Registration.from(id, vehicleType, licensePlate, rate, checkIn, checkOut, duration, amount);

        // Assert
        assertNotNull(registration);
        assertEquals(id, registration.getId());
        assertEquals(vehicleType, registration.getVehicle().getType().name());
        assertEquals(licensePlate, registration.getVehicle().getLicensePlate());
        assertEquals(checkIn, registration.getCheckIn());
        assertEquals(checkOut, registration.getCheckOut());
        assertEquals(duration, registration.getDuration());
        assertEquals(amount, registration.getAmount());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenCreatingRegistrationFromNullOrInvalidParameters() {
        // Arrange
        String id = null;
        String vehicleType = "CAR";
        String licensePlate = "ABC-1234";
        Double rate = 10.0;
        LocalDateTime checkIn = LocalDateTime.now();
        LocalDateTime checkOut = null;
        Duration duration = null;
        Double amount = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                Registration.from(id, vehicleType, licensePlate, rate, checkIn, checkOut, duration, amount));

        assertThrows(IllegalArgumentException.class, () ->
                Registration.from("1", null, licensePlate, rate, checkIn, checkOut, duration, amount));

        assertThrows(IllegalArgumentException.class, () ->
                Registration.from("1", vehicleType, null, rate, checkIn, checkOut, duration, amount));

        assertThrows(IllegalArgumentException.class, () ->
                Registration.from("1", vehicleType, licensePlate, rate, null, checkOut, duration, amount));
    }

    @Test
    public void shouldValidateThatIdIsAValidUUID() {
        Vehicle vehicle = new Car("ABC-1234", 10.0);
        LocalDateTime checkIn = LocalDateTime.now();
        Registration registration = new Registration(vehicle, checkIn);

        String id = registration.getId();
        assertNotNull(id);
        assertTrue(id.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"));
    }
}
