package com.parking.infrastructure;

import com.parking.infrastructure.repositories.RateRepository;
import com.parking.infrastructure.repositories.UserRepository;
import com.parking.infrastructure.repositories.entities.RateEntity;
import com.parking.infrastructure.repositories.entities.UserEntity;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.transaction.Transactional;

import java.util.UUID;

import static com.parking.infrastructure.configuration.security.JWTokenFactory.encode;

@QuarkusMain
public class Main implements QuarkusApplication {

    public static void main(String[] args) {
        Quarkus.run(args);
    }

    private final RateRepository rateRepository;
    private final UserRepository userRepository;

    public Main(RateRepository rateRepository, UserRepository userRepository) {
        this.rateRepository = rateRepository;
        this.userRepository = userRepository;
    }

    //TODO check how to run this method on startup
    @Override
    @Transactional
    public int run(String... args) throws Exception {

        rateRepository.deleteAll();
        userRepository.deleteAll();

        var rate = new RateEntity().setId(UUID.randomUUID().toString())
                .setVehicleType("CAR")
                .setRate(5.5);
        rateRepository.persist(rate);

        rate = new RateEntity().setId(UUID.randomUUID().toString())
                .setVehicleType("MOTORCYCLE")
                .setRate(3.1);
        rateRepository.persist(rate);

        var admin = new UserEntity();
        admin.setUsername("admin");
        admin.setPassword(encode("12345678"));
        admin.setRole("ADMIN");
        userRepository.persist(admin);


        return 0;
    }
}
