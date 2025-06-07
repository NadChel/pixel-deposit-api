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

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UserRepository repository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto findById(long userId) {
        User user = loadUser(userId);
        UserResponseDto userResponseDto = userMapper.toResponseDto(user);
        return userResponseDto;
    }

    private User loadUser(Long userId) {
        if (userId == null) throw new EntityNotFoundException("Update request has no id");
        Optional<User> userOptional = repository.findById(userId);
        if (userOptional.isEmpty()) throw new EntityNotFoundException("No such user");
        User user = userOptional.get();
        return user;
    }

    @Override
    @Transactional(readOnly = false)
    public UserResponseDto makeDeposit(long userId, DepositRequestDto depositDto) {
        User user = loadUser(userId);
        BigDecimal initialAccountBalance = user.getInitialAccountBalance();
        user.increaseAccountBalance(depositDto.getAmount());
        if (initialAccountBalance == null) startInterestAccrual(user);
        UserResponseDto userResponseDto = userMapper.toResponseDto(user);
        return userResponseDto;
    }

    private void startInterestAccrual(User user) {
        user.setInitialAccountBalance(user.getAccountBalance());
        Executors.newSingleThreadScheduledExecutor()
                .scheduleWithFixedDelay(() -> reloadAndAccrueInterest(user), 30, 30, TimeUnit.SECONDS);
    }

    private void reloadAndAccrueInterest(User user) {
        repository.findById(user.getId()).ifPresent(this::accrueInterest);
    }

    private void accrueInterest(User user) {
        BigDecimal newAccountBalance = calculateNewAccountBalance(user);
        if (newAccountBalance == null) return;
        user.setAccountBalance(newAccountBalance);
        repository.save(user);
    }

    private BigDecimal calculateNewAccountBalance(User user) {
        BigDecimal initialAccountBalance = user.getInitialAccountBalance();
        if (initialAccountBalance == null) return null;
        BigDecimal maxAccountBalance = initialAccountBalance.multiply(BigDecimal.valueOf(2.07));
        BigDecimal currentAccountBalance = user.getAccountBalance();
        if (maxAccountBalance.compareTo(currentAccountBalance) <= 0) return null;
        BigDecimal accountBalanceWithInterest = currentAccountBalance.multiply(BigDecimal.valueOf(1.1));
        BigDecimal newAccountBalance = accountBalanceWithInterest.min(maxAccountBalance);
        return newAccountBalance;
    }

    @Override
    @Transactional(readOnly = false)
    public UserResponseDto withdraw(long userId, DepositRequestDto depositDto) {
        User user = loadUser(userId);
        user.decreaseAccountBalance(depositDto.getAmount());
        UserResponseDto userResponseDto = userMapper.toResponseDto(user);
        return userResponseDto;
    }
}
