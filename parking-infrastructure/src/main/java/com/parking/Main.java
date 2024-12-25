package com.parking;

import com.parking.repositories.RateRepository;
import com.parking.repositories.entities.RateEntity;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public ApplicationRunner initRateEntity(RateRepository rateRepository) {
        return args -> {
           rateRepository.deleteAll();

            var rate = new RateEntity().setId(UUID.randomUUID().toString())
                    .setVehicleType("CAR")
                    .setRate(5.0);
            rateRepository.saveAndFlush(rate);

            rate = new RateEntity().setId(UUID.randomUUID().toString())
                    .setVehicleType("MOTORCYCLE")
                    .setRate(3.0);
            rateRepository.saveAndFlush(rate);

        };
    }
}
