package com.parking.infrastructure.repositories;

import com.parking.infrastructure.repositories.entities.RateEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class RateRepository implements PanacheRepository<RateEntity> {

    public Optional<RateEntity> findByVehicleType(String vehicleType) {
        return find("vehicleType", vehicleType).firstResultOptional();
    }
}
