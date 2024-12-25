package com.parking.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("parking")
public interface ParkingAPI {

    @PostMapping("/open/{capacity}")
    ResponseEntity<?> open(@PathVariable int capacity);

    @PostMapping("/register/{licensePlate}/{vehicleType}")
    ResponseEntity<?> register(@PathVariable String licensePlate, @PathVariable String vehicleType);

    @GetMapping("/report")
    ResponseEntity<?> report();

}
