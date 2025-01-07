package com.bank.shared.exception;

/**
 * Exception levée lorsque le solde est insuffisant pour effectuer une opération de retrait.
 */
public class InsufficientFundsException extends BankAccountException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}