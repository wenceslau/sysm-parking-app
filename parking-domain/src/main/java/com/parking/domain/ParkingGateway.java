package com.parking.domain;

import java.time.LocalDate;
import java.util.List;

public interface ParkingGateway {

    void save(Registration registration);

    double getRateByType(VehicleType vehicleType);

    List<Registration> findAllByCheckInDay(LocalDate localDate);

    List<Registration> loadAllByCurrentDay();

}
