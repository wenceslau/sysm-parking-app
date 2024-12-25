package com.parking.configuration;

import com.parking.ParkingGateway;
import com.parking.application.ParkingApp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParkingBean {

    private final ParkingGateway parkingGateway;

    public ParkingBean(ParkingGateway parkingGateway) {
        this.parkingGateway = parkingGateway;
    }

    @Bean
    public ParkingApp parkingApp() {
        return new ParkingApp(parkingGateway);
    }

}
