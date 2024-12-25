package com.parking.infrastructure.configuration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.parking.infrastructure.configuration.ExceptionHandler.ApiError.from;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(from(ex));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity
                .internalServerError()
                .body(from(ex));
    }

    record ApiError(String message) {
        public static Object from(Exception ex) {
            System.err.println(ex);
            return new ApiError(ex.getMessage());
        }
    }

}
