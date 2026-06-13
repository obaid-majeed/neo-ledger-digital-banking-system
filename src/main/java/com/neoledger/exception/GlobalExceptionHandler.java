package com.neoledger.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleRuntimeException(
            RuntimeException ex) {

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "timestamp",
                LocalDateTime.now());

        response.put(
                "message",
                ex.getMessage());

        return response;
    }

    @ExceptionHandler(
            DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleDuplicateException(
            DataIntegrityViolationException ex) {

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "timestamp",
                LocalDateTime.now());

        response.put(
                "message",
                "Duplicate data exists");

        return response;
    }
}