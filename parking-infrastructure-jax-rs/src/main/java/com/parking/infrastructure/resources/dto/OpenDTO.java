package com.parking.infrastructure.resources.dto;

import java.time.LocalDateTime;

public class OpenDTO {
    private int capacity;
    private LocalDateTime createdAt;

    public int getCapacity() {
        return capacity;
    }

    public OpenDTO setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public OpenDTO setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
