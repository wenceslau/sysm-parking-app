package com.parking.repositories;

import com.parking.repositories.entities.RegistrationEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class RegistrationRepository implements PanacheRepository<RegistrationEntity> {

    List<RegistrationEntity> findAllByCheckInGreaterThan(LocalDateTime checkIn) {
        return list("checkIn >= ?1", checkIn);
    }

}
