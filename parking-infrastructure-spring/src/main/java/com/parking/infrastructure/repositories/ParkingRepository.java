package com.parking.infrastructure.repositories;

import com.parking.infrastructure.repositories.entities.ParkingEntity;
import com.parking.infrastructure.repositories.entities.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ParkingRepository extends JpaRepository<ParkingEntity, String> {

    Optional<ParkingEntity> findByReferenceDate(LocalDate referenceDate);

}
