package com.parking.infrastructure.repositories;

import com.parking.infrastructure.repositories.entities.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface RegistrationRepository extends JpaRepository<RegistrationEntity, String> {

    List<RegistrationEntity> findAllByParkingId(String id);

    List<RegistrationEntity> findAllByCheckInGreaterThan(LocalDateTime checkIn);

    @Query("SELECT r FROM Registration r WHERE r.checkIn BETWEEN :start AND :end")
    List<RegistrationEntity> findAllByCheckInBetween(LocalDateTime start, LocalDateTime end);

}
