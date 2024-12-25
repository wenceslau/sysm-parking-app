package com.parking.infrastructure.controllers.records;

import java.util.List;

public record ReportResponse(
        List<ParkedResponse> parkedVehicles,
        List<CheckOutResponse> checkoutVehicles) {
}
