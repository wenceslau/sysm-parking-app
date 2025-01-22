package com.parking.domain;

import com.parking.domain.vehicles.Car;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    void shouldReturnZeroWhenMinutesAreZero() {
        // Arrange
        Vehicle vehicle = new Car("ABC-1234", 5.5);

        // Act
        double amount = vehicle.amountToPay(0);

        // Assert
        assertEquals(0, amount, "Amount should be 0 when minutes are 0");
    }

    @Test
    void shouldReturnZeroWhenMinutesAreFiveOrLess() {
        // Arrange
        Vehicle vehicle = new Car("ABC-1234", 5.5);

        // Act
        double amount = vehicle.amountToPay(5);

        // Assert
        assertEquals(0, amount, "Amount should be 0 when minutes are 5 or less");
    }

    @Test
    void shouldReturnBaseRateWhenMinutesAreBetweenSixAndSixty() {
        // Arrange
        Vehicle vehicle = new Car("ABC-1234", 5.5);

        // Act
        double amount = vehicle.amountToPay(30);

        // Assert
        assertEquals(5.5, amount, "Amount should be the base rate when minutes are between 6 and 60");
    }

    @Test
    void shouldCalculateCorrectAmountForExactlySixtyMinutes() {
        // Arrange
        Vehicle vehicle = new Car("ABC-1234", 5.5);

        // Act
        double amount = vehicle.amountToPay(60);

        // Assert
        assertEquals(5.5, amount, "Amount should be the base rate for exactly 60 minutes");
    }

    @Test
    void shouldCalculateCorrectAmountForSixtyOneMinutes() {
        // Arrange
        Vehicle vehicle = new Car("ABC-1234", 5.5);
        vehicle.setAdditionalHourValue(6.0);

        // Act
        double amount = vehicle.amountToPay(61);

        // Assert
        assertEquals(11.5, amount, "Amount should be base rate plus additional hour value for 61 minutes");
    }

    @Test
    void shouldCalculateCorrectAmountForMultipleFullHours() {
        // Arrange
        Vehicle vehicle = new Car("ABC-1234", 5.5);
        vehicle.setAdditionalHourValue(6.0);

        // Act
        double amount = vehicle.amountToPay(180); // 3 hours

        // Assert
        assertEquals(17.5, amount, "Amount should be base rate plus two additional hours for 180 minutes");
    }

    @Test
    void shouldCalculateCorrectAmountForMultipleHoursWithPartialHour() {
        // Arrange
        Vehicle vehicle = new Car("ABC-1234", 5.5);
        vehicle.setAdditionalHourValue(6.0);

        // Act
        double amount = vehicle.amountToPay(200); // 3 hours and 20 minutes

        // Assert
        assertEquals(23.5, amount, "Amount should be base rate plus three additional hours for 200 minutes");
    }

    @Test
    void shouldHandleVeryLargeNumberOfMinutesCorrectly() {
        // Arrange
        Vehicle vehicle = new Car("ABC-1234", 5.5);
        vehicle.setAdditionalHourValue(6.0);
        long veryLargeMinutes = 1000000; // Approximately 694 days

        // Act
        double amount = vehicle.amountToPay(veryLargeMinutes);

        // Assert
        double expectedAmount = 5.5 + (16666 * 6.0); // Base rate + (16666 full hours * additional hour value)
        assertEquals(expectedAmount, amount, 0.01, "Amount should be calculated correctly for very large number of minutes");
    }

    @Test
    void shouldRoundDownExtraMinutesThatDontConstituteFullHour() {
        // Arrange
        Vehicle vehicle = new Car("ABC-1234", 5.5);
        vehicle.setAdditionalHourValue(6.0);

        // Act
        double amount = vehicle.amountToPay(119); // 1 hour and 59 minutes

        // Assert
        assertEquals(11.5, amount, "Amount should be base rate plus one additional hour for 119 minutes");
    }

    @Test
    void shouldApplyAdditionalHourValueForAnyMinutesOverFullHours() {
        // Arrange
        Vehicle vehicle = new Car("ABC-1234", 5.5);
        vehicle.setAdditionalHourValue(6.0);

        // Act
        double amount = vehicle.amountToPay(121); // 2 hours and 1 minute

        // Assert
        assertEquals(17.5, amount, "Amount should be base rate plus two additional hours for 121 minutes");
    }


    @Test
    void shouldThrowExceptionWhenLicensePlateIsNull() {
        // Arrange
        String nullLicensePlate = null;
        double validRate = 5.5;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Car(nullLicensePlate, validRate);
        }, "Should throw IllegalArgumentException when license plate is null");
    }

    @Test
    void shouldThrowExceptionWhenLicensePlateIsEmpty() {
        // Arrange
        String emptyLicensePlate = "";
        double validRate = 5.5;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Car(emptyLicensePlate, validRate);
        }, "Should throw IllegalArgumentException when license plate is an empty string");
    }

    @Test
    void shouldThrowExceptionWhenRateIsNegative() {
        // Arrange
        String validLicensePlate = "ABC-1234";
        double negativeRate = -5.5;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Car(validLicensePlate, negativeRate);
        }, "Should throw IllegalArgumentException when rate is negative");
    }

    @Test
    void shouldThrowExceptionWhenRateIsZero() {
        // Arrange
        String validLicensePlate = "ABC-1234";
        double zeroRate = 0.0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Car(validLicensePlate, zeroRate);
        }, "Should throw IllegalArgumentException when rate is zero");
    }

    @Test
    void shouldCorrectlySetLicensePlateWhenValidStringIsProvided() {
        // Arrange
        String validLicensePlate = "ABC-1234";
        double validRate = 5.5;

        // Act
        Vehicle vehicle = new Car(validLicensePlate, validRate);

        // Assert
        assertEquals(validLicensePlate.toUpperCase(), vehicle.getLicensePlate(),
                "License plate should be set correctly and converted to uppercase");
    }

    @Test
    void shouldCorrectlySetRateWhenValidPositiveNumberIsProvided() {
        // Arrange
        String validLicensePlate = "ABC-1234";
        double validRate = 5.5;

        // Act
        Vehicle vehicle = new Car(validLicensePlate, validRate);

        // Assert
        assertEquals(validRate, vehicle.getRate(), "Rate should be correctly set when a valid positive number is provided");
    }

    @Test
    void shouldHandleVeryLongLicensePlateStrings() {
        // Arrange
        String veryLongLicensePlate = "A".repeat(1000) + "-1234";
        double validRate = 5.5;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Car(veryLongLicensePlate, validRate);
        }, "Should throw IllegalArgumentException when license plate is very long");
    }

    @Test
    void shouldHandleVeryLargeRateValuesWithoutOverflow() {
        // Arrange
        String validLicensePlate = "ABC-1234";
        double veryLargeRate = Double.MAX_VALUE;

        // Act & Assert
        assertDoesNotThrow(() -> {
            Vehicle vehicle = new Car(validLicensePlate, veryLargeRate);
            assertEquals(veryLargeRate, vehicle.getRate(), "Rate should be set to the very large value without overflow");
        }, "Should not throw an exception when initializing with a very large rate value");
    }

    @Test
    void shouldCreateDistinctObjectsForVehiclesWithSameLicensePlateAndRate() {
        // Arrange
        String licensePlate = "ABC-1234";
        double rate = 5.5;

        // Act
        Vehicle vehicle1 = new Car(licensePlate, rate);
        Vehicle vehicle2 = new Car(licensePlate, rate);

        // Assert
        assertNotSame(vehicle1, vehicle2, "Vehicles with the same license plate and rate should be distinct objects");
        assertEquals(vehicle1.getLicensePlate(), vehicle2.getLicensePlate(), "License plates should be equal");
        assertEquals(vehicle1.getRate(), vehicle2.getRate(), "Rates should be equal");
    }
}
