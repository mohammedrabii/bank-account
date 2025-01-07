package com.bank.shared.exception;

/**
 * Exception levée lorsqu'un compte avec le même identifiant existe déjà.
 */
public class AccountAlreadyExistsException extends BankAccountException {
    public AccountAlreadyExistsException(String message) {
        super(message);
    }
}
