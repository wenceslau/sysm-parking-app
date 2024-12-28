package com.parking.infrastructure.configuration;

import com.parking.domain.ParkingGateway;
import com.parking.application.ParkingApp;
import com.parking.application.ParkingAppImpl;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
@ComponentScan("com.parking.infrastructure")
public class AppParkingConfig {

    private final ParkingGateway parkingGateway;

    public AppParkingConfig(ParkingGateway parkingGateway) {
        this.parkingGateway = parkingGateway;
    }

    @Bean
    public ParkingApp parkingApp() {
        return new ParkingAppImpl(parkingGateway);
    }

}
