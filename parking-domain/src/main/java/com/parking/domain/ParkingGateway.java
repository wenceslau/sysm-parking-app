package com.parking.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ParkingGateway {

    void save(Registration registration);

    double getRateByType(VehicleType vehicleType);

    List<Registration> loadAllByCurrentDay();

    List<Registration> findAllByCheckInDay(LocalDate localDate);

    Optional<Parking> findParkingByCurrentDay();
}
