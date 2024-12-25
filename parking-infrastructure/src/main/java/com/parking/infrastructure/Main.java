package com.parking.infrastructure;

import com.parking.infrastructure.repositories.RateRepository;
import com.parking.infrastructure.repositories.entities.RateEntity;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
                    .setRate(5.5);
            rateRepository.saveAndFlush(rate);

            rate = new RateEntity().setId(UUID.randomUUID().toString())
                    .setVehicleType("MOTORCYCLE")
                    .setRate(3.1);
            rateRepository.saveAndFlush(rate);

        };
    }
}
