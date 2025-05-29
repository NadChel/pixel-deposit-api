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

    @PatchMapping("/add/{id}")
    @PreAuthorize("@userPermissionService.matchesCurrentUserId(#id)")
    public ResponseEntity<UserResponseDto> makeDeposit(@PathVariable Long id,
                                                       @RequestBody @Valid DepositRequestDto depositDto) {
        UserResponseDto userResponseDto = accountService.makeDeposit(id, depositDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @PatchMapping("/withdraw/{id}")
    @PreAuthorize("@userPermissionService.matchesCurrentUserId(#id)")
    public ResponseEntity<UserResponseDto> withdrawFromDeposit(@PathVariable Long id,
                                                               @RequestBody @Valid DepositRequestDto depositDto) {
        UserResponseDto userResponseDto = accountService.withdrawFromDeposit(id, depositDto);
        return ResponseEntity.ok(userResponseDto);
    }
}
