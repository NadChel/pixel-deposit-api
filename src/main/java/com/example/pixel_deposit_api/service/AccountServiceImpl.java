package com.example.pixel_deposit_api.service;

import com.example.pixel_deposit_api.data.dto.request.DepositRequestDto;
import com.example.pixel_deposit_api.data.dto.response.UserResponseDto;
import com.example.pixel_deposit_api.data.entity.User;
import com.example.pixel_deposit_api.mapper.UserMapper;
import com.example.pixel_deposit_api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UserRepository repository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = false)
    public UserResponseDto makeDeposit(long id, DepositRequestDto depositDto) {
        User user = loadUser(id);
        user.increaseAccountBalance(depositDto.getAmount());
        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional(readOnly = false)
    public UserResponseDto withdrawFromDeposit(long id, DepositRequestDto depositDto) {
        User user = loadUser(id);
        user.decreaseAccountBalance(depositDto.getAmount());
        return userMapper.toResponseDto(user);
    }

    private User loadUser(Long userId) {
        if (userId == null) throw new EntityNotFoundException("Update request has no id");
        Optional<User> userOptional = repository.findById(userId);
        if (userOptional.isEmpty()) throw new EntityNotFoundException("No such user");
        User user = userOptional.get();
        return user;
    }
}
