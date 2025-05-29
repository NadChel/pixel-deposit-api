package com.example.pixel_deposit_api.data.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DepositRequestDto {

    @NotNull(message = "Must be present")
    @Positive(message = "Must be positive")
    private BigDecimal amount;
}
