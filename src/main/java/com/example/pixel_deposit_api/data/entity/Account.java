package com.example.pixel_deposit_api.data.entity;

import com.example.pixel_deposit_api.exception.InsufficientBalanceException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private BigDecimal balance = BigDecimal.ZERO;

    public static Account forUser(User user) {
        Account account = new Account();
        account.setUser(user);
        return account;
    }

    public void increaseBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void decreaseBalance(BigDecimal amount) {
        BigDecimal newBalance = balance.subtract(amount);
        if (isNegative(newBalance)) throw new InsufficientBalanceException();
        balance = newBalance;
    }

    private static boolean isNegative(BigDecimal newBalance) {
        return newBalance.compareTo(BigDecimal.ZERO) < 0;
    }
}
