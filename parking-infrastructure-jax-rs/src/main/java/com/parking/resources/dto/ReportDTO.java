package com.parking.resources.dto;

import java.util.List;

public class ReportDTO {

    private List<ParkedDTO> parkedVehicles;
    private List<CheckOutDTO> checkoutVehicles;

    public List<ParkedDTO> getParkedVehicles() {
        return parkedVehicles;
    }

    public ReportDTO setParkedVehicles(List<ParkedDTO> parkedVehicles) {
        this.parkedVehicles = parkedVehicles;
        return this;
    }

    public List<CheckOutDTO> getCheckoutVehicles() {
        return checkoutVehicles;
    }

    public ReportDTO setCheckoutVehicles(List<CheckOutDTO> checkoutVehicles) {
        this.checkoutVehicles = checkoutVehicles;
        return this;
    }
}
