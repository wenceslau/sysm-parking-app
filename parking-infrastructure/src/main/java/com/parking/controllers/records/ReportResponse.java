package com.parking.controllers.records;

import java.util.List;

public record ReportResponse(
        List<ParkedResponse> parkedVehicles,
        List<CheckOutResponse> checkoutVehicles) {
}
