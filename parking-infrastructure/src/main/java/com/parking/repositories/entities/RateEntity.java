package com.parking.repositories.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "Rate")
@Table(name = "rates")
public class RateEntity {

    @Id
    public String id;

    @Column(name = "vehicle_type")
    public String vehicleType;

    @Column(name = "rate")
    public double rate;

    public String getId() {
        return id;
    }

    public RateEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public RateEntity setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public double getRate() {
        return rate;
    }

    public RateEntity setRate(double rate) {
        this.rate = rate;
        return this;
    }
}
