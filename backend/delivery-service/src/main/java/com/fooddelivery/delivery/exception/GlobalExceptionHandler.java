package com.fooddelivery.delivery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Delivery already exists
    @ExceptionHandler(DeliveryAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleDeliveryAlreadyExists(
            DeliveryAlreadyExistsException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", exception.getMessage()));
    }

    // Delivery not found
    @ExceptionHandler(DeliveryNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDeliveryNotFound(
            DeliveryNotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", exception.getMessage()));
    }

    // Invalid delivery status transition
    @ExceptionHandler(InvalidDeliveryStatusException.class)
    public ResponseEntity<Map<String, String>> handleInvalidDeliveryStatus(
            InvalidDeliveryStatusException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", exception.getMessage()));
    }

    // Request validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }
}