package com.parking.repositories;

import com.parking.ParkingGateway;
import com.parking.Registration;
import com.parking.VehicleType;
import com.parking.repositories.entities.RateEntity;
import com.parking.repositories.entities.RegistrationEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ParkingGatewayDB implements ParkingGateway {

    private final RateRepository rateRepository;
    private final RegistrationRepository parkingRepository;

    @Inject
    public ParkingGatewayDB(RateRepository rateRepository, RegistrationRepository parkingRepository) {
        this.rateRepository = rateRepository;
        this.parkingRepository = parkingRepository;
    }

    @Override
    public void save(Registration registration) {
        parkingRepository.persist(toEntity(registration));
    }

    @Override
    public double getRateByType(VehicleType vehicleType) {
        var optionalRate = rateRepository.findByVehicleType(vehicleType.toString());
        return optionalRate
                .map(RateEntity::getRate)
                .orElseThrow(() -> new EntityNotFoundException("Rate not found for vehicle type: " + vehicleType));
    }

    @Override
    public List<Registration> loadAllByCurrentDay() {
        var currentDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);

        return parkingRepository.findAllByCheckInGreaterThan(currentDay)
                .stream()
                .map(ParkingGatewayDB::toDomain)
                .toList();
    }

    private static RegistrationEntity toEntity(Registration registration) {
        return new RegistrationEntity()
                .setId(registration.getId())
                .setLicensePlate(registration.getVehicle().getLicensePlate())
                .setVehicleType(registration.getVehicle().getClass().getSimpleName().toUpperCase())
                .setRate(registration.getVehicle().getRate())
                .setCheckIn(registration.getCheckIn())
                .setCheckOut(registration.getCheckOut())
                .setAmount(registration.getAmount());
    }

    private static Registration toDomain(RegistrationEntity registrationEntity) {
        Duration duration = null;
        if (registrationEntity.getCheckOut() != null) {
            duration = Duration.between(registrationEntity.getCheckIn(), registrationEntity.getCheckOut());
        }
        return Registration.from(
                registrationEntity.getId(),
                registrationEntity.getVehicleType(),
                registrationEntity.getLicensePlate(),
                registrationEntity.getRate(),
                registrationEntity.getCheckIn(),
                registrationEntity.getCheckOut(),
                duration,
                registrationEntity.getAmount());
    }
}
