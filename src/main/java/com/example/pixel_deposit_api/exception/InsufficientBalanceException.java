package com.example.pixel_deposit_api.exception;

public class InsufficientBalanceException extends AccountException {

    public InsufficientBalanceException() {
        this("Insufficient balance");
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
