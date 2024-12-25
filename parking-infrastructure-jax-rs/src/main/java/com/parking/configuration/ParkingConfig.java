package com.parking.configuration;

import com.parking.ParkingGateway;
import com.parking.application.ParkingApp;
import com.parking.application.ParkingAppImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

@ApplicationScoped
public class ParkingConfig {

    @Inject
    private ParkingGateway parkingGateway;

    @Produces
    public ParkingApp parkingAppProducer() {
        return new ParkingAppImpl(parkingGateway);
    }

}
