package com.bank.shared.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {
    DEPOSIT_AMOUNT_NEGATIVE("Deposit amount must be positive"),
    WITHDRAWAL_AMOUNT_NEGATIVE("Withdrawal amount must be positive"),
    INSUFFICIENT_FUNDS("Insufficient funds for withdrawal"),
    ACCOUNT_NOT_FOUND("Account not found with ID: %s"),
    ACCOUNT_ALREADY_EXISTS("Account with the given ID already exists");

    private final String message;

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
