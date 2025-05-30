package com.example.pixel_deposit_api.service;

import com.example.pixel_deposit_api.data.dto.request.DepositRequestDto;
import com.example.pixel_deposit_api.data.dto.response.UserResponseDto;
import org.springframework.transaction.annotation.Transactional;

public interface AccountService {

    UserResponseDto findById(long userId);

    UserResponseDto makeDeposit(long userId, DepositRequestDto depositDto);

    @Transactional(readOnly = false)
    UserResponseDto withdrawFromDeposit(long userId, DepositRequestDto depositDto);
}
