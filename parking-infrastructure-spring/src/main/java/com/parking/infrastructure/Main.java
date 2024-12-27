package com.parking.infrastructure;

import com.parking.infrastructure.repositories.RateRepository;
import com.parking.infrastructure.repositories.UserRepository;
import com.parking.infrastructure.repositories.entities.RateEntity;
import com.parking.infrastructure.repositories.entities.UserEntity;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@EnableCaching
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public ApplicationRunner initRateEntity(RateRepository rateRepository,
                                            UserRepository userRepository,
                                            PasswordEncoder passwordEncoder) {
        return args -> {
           rateRepository.deleteAll();
           userRepository.deleteAll();

            var rate = new RateEntity().setId(UUID.randomUUID().toString())
                    .setVehicleType("CAR")
                    .setRate(5.5);
            rateRepository.saveAndFlush(rate);

            rate = new RateEntity().setId(UUID.randomUUID().toString())
                    .setVehicleType("MOTORCYCLE")
                    .setRate(3.1);
            rateRepository.saveAndFlush(rate);

            var admin = new UserEntity();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("12345678"));
            admin.setRole("ADMIN");
            userRepository.save(admin);

        };
    }
}
