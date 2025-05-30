package com.example.pixel_deposit_api.controller.advice;

import com.example.pixel_deposit_api.exception.InsufficientBalanceException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("\n"));
        return badRequestWithMessage(message);
    }

    private static ResponseEntity<String> badRequestWithMessage(String message) {
        ResponseEntity<String> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        return response;
    }

    @ExceptionHandler
    public ResponseEntity<String> handleConstraintViolation(MethodArgumentNotValidException e) {
        String message = createObjectErrorMessage(e);
        return badRequestWithMessage(message);
    }

    private static String createObjectErrorMessage(MethodArgumentNotValidException e) {
        Stream<String> globalErrorMessages = e.getGlobalErrors().stream()
                .map(ObjectError::getDefaultMessage);
        Stream<String> fieldErrorMessages = e.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage());
        String message = Stream.concat(globalErrorMessages, fieldErrorMessages)
                .collect(Collectors.joining("\n"));
        return message;
    }

    @ExceptionHandler
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException e) {
        return badRequestWithMessage(e);
    }

    private static ResponseEntity<String> badRequestWithMessage(Exception e) {
        String message = e.getMessage();
        return badRequestWithMessage(message);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleInsufficientBalance(InsufficientBalanceException e) {
        return badRequestWithMessage(e);
    }
}
