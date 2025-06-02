package com.example.pixel_deposit_api.controller;

import com.example.pixel_deposit_api.data.dto.request.DepositRequestDto;
import com.example.pixel_deposit_api.data.dto.response.UserResponseDto;
import com.example.pixel_deposit_api.service.AccountService;
import com.example.pixel_deposit_api.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(description = "Returns account details of the user associated with the provided token.")
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
    @Operation(description = """
            Makes a deposit to this user's account, creating one if necessary.
            If it's a first deposit, this operation will initiate interest accrual.""")
    public ResponseEntity<UserResponseDto> makeDeposit(@RequestBody @Valid DepositRequestDto depositDto) {
        Long userId = getUserId();
        UserResponseDto userResponseDto = accountService.makeDeposit(userId, depositDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @PatchMapping("/withdraw")
    @Operation(description = "Withdraws from this user's account.")
    public ResponseEntity<UserResponseDto> withdrawFromDeposit(@RequestBody @Valid DepositRequestDto depositDto) {
        Long userId = getUserId();
        UserResponseDto userResponseDto = accountService.withdraw(userId, depositDto);
        return ResponseEntity.ok(userResponseDto);
    }
}
