package com.parking.infrastructure.repositories;

import com.parking.domain.Parking;
import com.parking.domain.ParkingGateway;
import com.parking.domain.Registration;
import com.parking.domain.VehicleType;
import com.parking.infrastructure.repositories.entities.ParkingEntity;
import com.parking.infrastructure.repositories.entities.RegistrationEntity;
import com.parking.infrastructure.repositories.entities.RateEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ParkingGatewayDB implements ParkingGateway {

    private final RateRepository rateRepository;
    private final RegistrationRepository registrationRepository;
    private final ParkingRepository parkingRepository;

    @Inject
    public ParkingGatewayDB(RateRepository rateRepository, RegistrationRepository parkingRepository, ParkingRepository parkingRepository1) {
        this.rateRepository = rateRepository;
        this.registrationRepository = parkingRepository;
        this.parkingRepository = parkingRepository1;
    }

    @Override
    @Transactional
    public void save(Registration registration) {
        var entity = toEntity(registration);
        if (registration.getCheckOut() != null) {
            entity = registrationRepository.getEntityManager().merge(entity);
        }
        registrationRepository.persist(entity);
    }

    @Override
    public double getRateByType(VehicleType vehicleType) {
        var optionalRate = rateRepository.findByVehicleType(vehicleType.toString());
        return optionalRate
                .map(RateEntity::getRate)
                .orElseThrow(() -> new EntityNotFoundException("Rate not found for vehicle type: " + vehicleType));
    }

    public List<Registration> loadAllByCurrentDay() {
        var currentDay = LocalDateTime.now()
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        return registrationRepository.findAllByCheckInGreaterThan(currentDay)
                .stream()
                .map(ParkingGatewayDB::toDomain)
                .toList();
    }

    @Override
    public List<Registration> findAllByCheckInDay(LocalDate localDate) {
        var currentDay = localDate.atStartOfDay();
        var nextDay = localDate.atTime(23, 59, 59);

        return registrationRepository.findAllByCheckInBetween(currentDay, nextDay)
                .stream()
                .map(ParkingGatewayDB::toDomain)
                .toList();
    }

    @Override
    public Optional<Parking> findParkingByCurrentDay() {
        var parking = parkingRepository.findByReferenceDate(LocalDate.now());
        if (parking.isPresent()) {
            var registrations = registrationRepository.findAllByParkingId(parking.get().getId());
            return Optional.of(toDomain(parking.get(), registrations));
        } else {
            return Optional.empty();
        }
    }

    private static RegistrationEntity toEntity(Registration registration) {
        return new RegistrationEntity()
                .setId(registration.getId())
                .setLicensePlate(registration.getVehicle().getLicensePlate())
                .setVehicleType(registration.getVehicle().getType().name())
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

    private static Parking toDomain(ParkingEntity parkingEntity, List<RegistrationEntity> registrationEntities) {
        var registrations = registrationEntities.stream()
                .map(ParkingGatewayDB::toDomain)
                .toList();
        return new Parking(parkingEntity.getId(), parkingEntity.getReferenceDate(), registrations);
    }
}
