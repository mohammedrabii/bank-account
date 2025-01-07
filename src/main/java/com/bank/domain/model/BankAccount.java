package com.bank.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class BankAccount {
    private UUID id;
    private Double balance;
    private List<AccountOperation> accountOperations;

    public void addOperation(AccountOperation operation) {
        accountOperations.add(operation);
    }

    public void updateBalance(double newBalance) {
        this.balance = newBalance;
    }
}
