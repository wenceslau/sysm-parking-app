package com.parking.infrastructure.repositories;

import com.parking.infrastructure.repositories.entities.ParkingEntity;
import com.parking.infrastructure.repositories.entities.RateEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.Optional;

@ApplicationScoped
public class ParkingRepository implements PanacheRepository<ParkingEntity> {

    public Optional<ParkingEntity> findByReferenceDate(LocalDate referenceDate) {
        return find("referenceDate", referenceDate).firstResultOptional();
    }
}
