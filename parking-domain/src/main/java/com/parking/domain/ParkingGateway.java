package com.parking.domain;

import java.util.List;

public interface ParkingGateway {

    void save(Registration registration);

    double getRateByType(VehicleType vehicleType);

    List<Registration> loadAllByCurrentDay();
}
