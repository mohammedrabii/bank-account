package com.bank.domain.port;

import com.bank.domain.model.AccountOperation;
import com.bank.domain.model.BankAccount;

import java.util.List;
import java.util.UUID;

public interface BankAccountPersistence {
    BankAccount saveAccount(BankAccount account);

    BankAccount findAccountById(UUID accountId);

    void addOperation(UUID accountId, AccountOperation operation);

    List<AccountOperation> getOperations(UUID accountId);

}
