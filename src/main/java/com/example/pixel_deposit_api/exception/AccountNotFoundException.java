package com.example.pixel_deposit_api.exception;

public class AccountNotFoundException extends AccountException {

    public AccountNotFoundException() {
        this("No account found");
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
