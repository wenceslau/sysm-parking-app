package com.parking.infrastructure.controllers.records;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
       @NotBlank String username,
       @NotBlank String password
) {
}
