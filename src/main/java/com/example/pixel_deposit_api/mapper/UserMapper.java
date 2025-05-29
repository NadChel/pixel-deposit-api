package com.example.pixel_deposit_api.mapper;

import com.example.pixel_deposit_api.data.dto.response.UserResponseDto;
import com.example.pixel_deposit_api.data.entity.Account;
import com.example.pixel_deposit_api.data.entity.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    UserResponseDto toResponseDto(User user);

    @AfterMapping
    default void setDepositAmount(@MappingTarget UserResponseDto userResponseDto, User user) {
        Account account = user.getAccount();
        userResponseDto.setBalance(account.getBalance());
    }
}
