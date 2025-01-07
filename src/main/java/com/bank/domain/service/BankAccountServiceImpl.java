package com.bank.domain.service;

import com.bank.api.model.BankAccountDto;
import com.bank.domain.incomingservice.BankAccountService;
import com.bank.domain.model.BankAccount;
import com.bank.domain.port.BankAccountPersistence;
import com.bank.shared.exception.AccountAlreadyExistsException;
import com.bank.shared.exception.ErrorMessages;

import java.util.UUID;

public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountPersistence bankAccountPersistence;
    private static final double INITIAL_BALANCE = 0.0;

    public BankAccountServiceImpl(BankAccountPersistence bankAccountPersistence) {
        this.bankAccountPersistence = bankAccountPersistence;
    }

    /**
     * Crée un nouveau compte bancaire avec un solde initial de 0.
     *
     * @return les détails du compte créé.
     * @throws AccountAlreadyExistsException si le compte existe
     */
    public BankAccountDto createAccount() {
        BankAccount newAccount = BankAccount
                .builder()
                .id(UUID.randomUUID())
                .balance(INITIAL_BALANCE).build();

        if (bankAccountPersistence.findAccountById(newAccount.getId()) != null) {
            throw new AccountAlreadyExistsException(ErrorMessages.ACCOUNT_ALREADY_EXISTS.getMessage());
        }
        BankAccount account = bankAccountPersistence.saveAccount(newAccount);
        return BankAccountDto.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .accountOperations(account.getAccountOperations())
                .build();
    }


}
