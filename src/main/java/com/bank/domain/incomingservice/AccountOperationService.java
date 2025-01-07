package com.bank.domain.incomingservice;


import java.util.UUID;

public interface AccountOperationService {
    void deposit(UUID accountId, double amount);

    void withdraw(UUID accountId, double amount);
}
