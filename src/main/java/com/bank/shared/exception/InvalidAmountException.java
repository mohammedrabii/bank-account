package com.bank.shared.exception;

/**
 * Exception levée lorsqu'un montant de dépôt est invalide.
 */
public class InvalidAmountException extends BankAccountException {
    public InvalidAmountException(String message) {
        super(message);
    }
}