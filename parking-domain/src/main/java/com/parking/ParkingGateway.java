package com.parking;

import java.time.LocalDateTime;
import java.util.List;

public interface ParkingGateway {

    void save(Registration registration);

    double getRateByType(VehicleType vehicleType);

    List<Registration> loadAllByCurrentDay();
}
