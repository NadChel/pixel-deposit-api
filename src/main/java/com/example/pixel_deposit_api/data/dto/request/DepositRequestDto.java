package com.example.pixel_deposit_api.data.dto.request;

import jakarta.validation.constraints.Digits;
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
    @Digits(integer = 20, fraction = 2,
            message = "Must have at most 20 digits before, 2 digits after the decimal separator")
    private BigDecimal amount;
}
