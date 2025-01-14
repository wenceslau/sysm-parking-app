package com.parking.infrastructure.controllers.records;

import java.time.LocalDateTime;
import java.util.List;

public record RegisterResponse(
        String type,
        String plate,
        String vehicleType,
        Double rate,
        LocalDateTime checkIn,
        LocalDateTime checkOut,
        Long duration,
        Double amountToPay,
        List<CheckInResponse> parkedVehicles,
        List<CheckOutResponse> checkoutVehicles) {
}
