package com.parking.infrastructure.repositories;

import com.parking.domain.Parking;
import com.parking.domain.ParkingGateway;
import com.parking.domain.Registration;
import com.parking.domain.VehicleType;
import com.parking.infrastructure.repositories.entities.ParkingEntity;
import com.parking.infrastructure.repositories.entities.RateEntity;
import com.parking.infrastructure.repositories.entities.RegistrationEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingGatewayDB implements ParkingGateway {

    private final RateRepository rateRepository;
    private final RegistrationRepository registrationRepository;
    private final ParkingRepository parkingRepository;

    public ParkingGatewayDB(RateRepository rateRepository,
                            RegistrationRepository registrationRepository,
                            ParkingRepository parkingRepository) {
        this.rateRepository = rateRepository;
        this.registrationRepository = registrationRepository;
        this.parkingRepository = parkingRepository;
    }

    @Override
    public void save(Registration registration) {
        registrationRepository.save(toEntity(registration));
    }

    @Override
    public double getRateByType(VehicleType vehicleType) {
        var optionalRate = rateRepository.findByVehicleType(vehicleType.toString());
        return optionalRate
                .map(RateEntity::getRate)
                .orElseThrow(()-> new EntityNotFoundException("Rate not found for vehicle type: " + vehicleType));
    }

   // @Override
//    public List<Registration> loadAllByCurrentDay() {
//        var currentDay = LocalDateTime.now()
//                .withHour(0)
//                .withMinute(0)
//                .withSecond(0)
//                .withNano(0);
//
//        return registrationRepository.findAllByCheckInGreaterThan(currentDay)
//                .stream()
//                .map(ParkingGatewayDB::toDomain)
//                .toList();
//    }

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

    @Override
    public List<Registration> findAllByCheckInDay(LocalDate localDate) {

        var start = localDate.atStartOfDay();
        var end = localDate.atTime(23, 59, 59);

        return registrationRepository.findAllByCheckInBetween(start, end)
                .stream()
                .map(ParkingGatewayDB::toDomain)
                .toList();
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
