package com.parking.infrastructure.repositories.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "Parking")
@Table(name = "parking")
public class ParkingEntity {

    @Id
    private String id;

    @Column(name = "reference_date")
    private LocalDate referenceDate;

    @Column(name = "capacity")
    private Integer capacity;

    public String getId() {
        return id;
    }

    public ParkingEntity setId(String id) {
        this.id = id;
        return this;
    }

    public LocalDate getReferenceDate() {
        return referenceDate;
    }

    public ParkingEntity setReferenceDate(LocalDate referenceDate) {
        this.referenceDate = referenceDate;
        return this;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public ParkingEntity setCapacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }
}
