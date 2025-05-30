package com.example.pixel_deposit_api.controller;

import com.example.pixel_deposit_api.data.dto.request.DepositRequestDto;
import com.example.pixel_deposit_api.data.dto.response.UserResponseDto;
import com.example.pixel_deposit_api.service.AccountService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{user-id}")
    @PreAuthorize("@userPermissionService.matchesCurrentUserId(#userId)")
    public ResponseEntity<UserResponseDto> getAccountBalance(@PathVariable("user-id") Long userId) {
        UserResponseDto userResponseDto = accountService.findById(userId);
        return ResponseEntity.ok(userResponseDto);
    }

    @PatchMapping("/add/{user-id}")
    @PreAuthorize("@userPermissionService.matchesCurrentUserId(#userId)")
    public ResponseEntity<UserResponseDto> makeDeposit(@PathVariable("user-id") Long userId,
                                                       @RequestBody @Valid DepositRequestDto depositDto) {
        UserResponseDto userResponseDto = accountService.makeDeposit(userId, depositDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @PatchMapping("/withdraw/{user-id}")
    @PreAuthorize("@userPermissionService.matchesCurrentUserId(#user-id)")
    public ResponseEntity<UserResponseDto> withdrawFromDeposit(@PathVariable("user-id") Long userId,
                                                               @RequestBody @Valid DepositRequestDto depositDto) {
        UserResponseDto userResponseDto = accountService.withdrawFromDeposit(userId, depositDto);
        return ResponseEntity.ok(userResponseDto);
    }
}
