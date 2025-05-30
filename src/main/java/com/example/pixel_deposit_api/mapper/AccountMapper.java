package com.example.pixel_deposit_api.mapper;

import com.example.pixel_deposit_api.data.dto.response.AccountResponseDto;
import com.example.pixel_deposit_api.data.entity.Account;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AccountMapper {

    AccountResponseDto toResponseDto(Account account);
}
