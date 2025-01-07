package com.bank.domain.port;

import com.bank.domain.model.BankAccount;

import java.util.UUID;

public interface BankAccountPersistence {
    BankAccount saveAccount(BankAccount account);

    BankAccount findAccountById(UUID accountId);

}
