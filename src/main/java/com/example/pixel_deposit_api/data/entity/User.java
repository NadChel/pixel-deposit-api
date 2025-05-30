package com.example.pixel_deposit_api.data.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Account account;

    @Nullable
    public BigDecimal getInitialAccountBalance() {
        BigDecimal initialAccountBalance = account == null ?
                null : account.getInitialBalance();
        return initialAccountBalance;
    }

    public BigDecimal getAccountBalance() {
        BigDecimal accountBalance = account == null ?
                BigDecimal.ZERO : account.getBalance();
        return accountBalance;
    }

    public void setInitialAccountBalance(BigDecimal initialAccountBalance) {
        ensureAccountSet();
        account.setInitialBalance(initialAccountBalance);
    }

    public void setAccountBalance(BigDecimal newBalance) {
        ensureAccountSet();
        account.setBalance(newBalance);
    }

    public void increaseAccountBalance(BigDecimal amount) {
        ensureAccountSet();
        account.increaseBalance(amount);
    }

    private void ensureAccountSet() {
        if (account == null) account = Account.forUser(this);
    }

    public void decreaseAccountBalance(BigDecimal amount) {
        ensureAccountSet();
        account.decreaseBalance(amount);
    }
}
