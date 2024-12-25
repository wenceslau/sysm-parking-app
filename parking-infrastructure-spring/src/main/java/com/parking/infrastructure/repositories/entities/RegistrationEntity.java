package com.parking.infrastructure.repositories.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity(name = "Registration")
@Table(name = "registrations")
public class RegistrationEntity {

    @Id
    private String id;

    @Column(name = "license_plate")
    private String licensePlate;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "check_in")
    private LocalDateTime checkIn;

    @Column(name = "check_out")
    private LocalDateTime checkOut;

    @Column(name = "amount")
    private Double amount;

    public String getId() {
        return id;
    }

    public RegistrationEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public RegistrationEntity setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
        return this;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public RegistrationEntity setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public Double getRate() {
        return rate;
    }

    public RegistrationEntity setRate(Double rate) {
        this.rate = rate;
        return this;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public RegistrationEntity setCheckIn(LocalDateTime checkin) {
        this.checkIn = checkin;
        return this;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public RegistrationEntity setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
        return this;
    }

    public Double getAmount() {
        return amount;
    }

    public RegistrationEntity setAmount(Double amount) {
        this.amount = amount;
        return this;
    }
}
