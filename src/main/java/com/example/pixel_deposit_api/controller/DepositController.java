package com.example.pixel_deposit_api.controller;

import com.example.pixel_deposit_api.data.dto.request.DepositRequestDto;
import com.example.pixel_deposit_api.data.dto.response.UserResponseDto;
import com.example.pixel_deposit_api.service.AccountService;
import com.example.pixel_deposit_api.service.AuthenticationService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/deposit")
@RequiredArgsConstructor
@SecurityScheme(type = SecuritySchemeType.HTTP,
        name = "bearer-key",
        scheme = "bearer", bearerFormat = "JWT")
public class DepositController {

    private final AccountService accountService;
    private final AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<UserResponseDto> getAccountBalance() {
        Long userId = getUserId();
        UserResponseDto userResponseDto = accountService.findById(userId);
        return ResponseEntity.ok(userResponseDto);
    }

    private Long getUserId() {
        Long userId = authenticationService.getCurrentUserId()
                .orElseThrow(this::authenticationException);
        return userId;
    }

    private AuthenticationException authenticationException() {
        String message = "No authenticated claim found";
        return new AuthenticationCredentialsNotFoundException(message);
    }

    @PatchMapping("/add")
    public ResponseEntity<UserResponseDto> makeDeposit(@RequestBody @Valid DepositRequestDto depositDto) {
        Long userId = getUserId();
        UserResponseDto userResponseDto = accountService.makeDeposit(userId, depositDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @PatchMapping("/withdraw")
    public ResponseEntity<UserResponseDto> withdrawFromDeposit(@RequestBody @Valid DepositRequestDto depositDto) {
        Long userId = getUserId();
        UserResponseDto userResponseDto = accountService.withdrawFromDeposit(userId, depositDto);
        return ResponseEntity.ok(userResponseDto);
    }
}
