package com.example.pixel_deposit_api.service;

import com.example.pixel_deposit_api.data.dto.request.DepositRequestDto;
import com.example.pixel_deposit_api.data.dto.response.UserResponseDto;

public interface AccountService {

    UserResponseDto findById(long userId);

    UserResponseDto makeDeposit(long userId, DepositRequestDto depositDto);

    UserResponseDto withdraw(long userId, DepositRequestDto depositDto);
}
