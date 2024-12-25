package com.parking.infrastructure.repositories;

import com.parking.infrastructure.repositories.entities.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RegistrationRepository extends JpaRepository<RegistrationEntity, String> {

    List<RegistrationEntity> findAllByCheckInGreaterThan(LocalDateTime checkIn);

}
