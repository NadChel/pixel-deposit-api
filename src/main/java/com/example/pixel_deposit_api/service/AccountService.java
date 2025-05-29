package com.example.pixel_deposit_api.service;

import com.example.pixel_deposit_api.data.dto.request.DepositRequestDto;
import com.example.pixel_deposit_api.data.dto.response.UserResponseDto;
import org.springframework.transaction.annotation.Transactional;

public interface AccountService {

    UserResponseDto makeDeposit(long id, DepositRequestDto depositDto);

    @Transactional(readOnly = false)
    UserResponseDto withdrawFromDeposit(long id, DepositRequestDto depositDto);
}
