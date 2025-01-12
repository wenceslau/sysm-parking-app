package com.parking.infrastructure.controllers;

import com.parking.infrastructure.controllers.records.AuthRequest;
import com.parking.infrastructure.controllers.records.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequestMapping("parking")
public interface ParkingAPI {

    @GetMapping("/status")
    ResponseEntity<?> status();

    @PostMapping("/open/{capacity}")
    ResponseEntity<?> open(@PathVariable int capacity);

    @PostMapping("/register")
    ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest);

    @GetMapping("/report")
    ResponseEntity<?> report();

    @GetMapping("/report/{date}")
    ResponseEntity<?> report(@PathVariable LocalDate date);

    @PostMapping("/auth")
    ResponseEntity<?> auth(@RequestBody AuthRequest authRequest);

}
