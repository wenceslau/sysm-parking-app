package com.parking.infrastructure.configuration;

import com.parking.domain.ParkingGateway;
import com.parking.application.ParkingApp;
import com.parking.application.ParkingAppImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

@ApplicationScoped
public class ParkingProduced {

    @Inject
    ParkingGateway parkingGateway;

    @Produces
    public ParkingApp parkingAppProducer() {
        return new ParkingAppImpl(parkingGateway);
    }

}
