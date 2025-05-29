package com.example.pixel_deposit_api.data.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserResponseDto {

    private Long id;
    private String name;
    private BigDecimal balance;
}
