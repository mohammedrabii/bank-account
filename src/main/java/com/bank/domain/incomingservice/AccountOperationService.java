package com.bank.domain.incomingservice;


import com.bank.api.model.AccountOperationDto;

import java.util.List;
import java.util.UUID;

public interface AccountOperationService {
    void deposit(UUID accountId, double amount);

    void withdraw(UUID accountId, double amount);

    List<AccountOperationDto> getOperationsHistory(UUID accountId);
}
