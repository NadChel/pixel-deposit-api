package com.example.pixel_deposit_api.controller.advice;

import com.example.pixel_deposit_api.exception.AccountException;
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
        return unprocessableEntityWithMessage(message);
    }

    private static ResponseEntity<String> unprocessableEntityWithMessage(String message) {
        ResponseEntity<String> response = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(message);
        return response;
    }

    @ExceptionHandler
    public ResponseEntity<String> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String message = createObjectErrorMessage(e);
        return unprocessableEntityWithMessage(message);
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
        return unprocessableEntityWithMessage(e);
    }

    private static ResponseEntity<String> unprocessableEntityWithMessage(Exception e) {
        String message = e.getMessage();
        return unprocessableEntityWithMessage(message);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleAccount(AccountException e) {
        return unprocessableEntityWithMessage(e);
    }
}
