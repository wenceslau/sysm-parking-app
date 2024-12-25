package com.parking.repositories;

import com.parking.repositories.entities.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RateRepository extends JpaRepository<RateEntity, String> {

    Optional<RateEntity> findByVehicleType(String vehicleType);

}
