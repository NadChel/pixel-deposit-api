package com.example.pixel_deposit_api.exception;

public abstract class AccountException extends RuntimeException {

    public AccountException(String message) {
        super(message);
    }
}
