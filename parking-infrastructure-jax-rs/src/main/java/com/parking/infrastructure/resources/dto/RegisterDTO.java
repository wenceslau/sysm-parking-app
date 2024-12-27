package com.parking.infrastructure.resources.dto;

import jakarta.validation.constraints.NotBlank;

public class RegisterDTO {
    @NotBlank
    private String vehicleType;

    @NotBlank
    private String licensePlate;

    public String getVehicleType() {
        return vehicleType;
    }

    public RegisterDTO setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public RegisterDTO setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
        return this;
    }
}
