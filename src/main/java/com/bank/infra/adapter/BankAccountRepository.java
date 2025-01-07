package com.bank.infra.adapter;

import com.bank.domain.model.AccountOperation;
import com.bank.domain.model.BankAccount;
import com.bank.domain.port.BankAccountPersistence;
import com.bank.shared.exception.AccountAlreadyExistsException;
import com.bank.shared.exception.BankAccountException;
import com.bank.shared.exception.ErrorMessages;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implémentation en mémoire du dépôt de données pour les comptes bancaires.
 */
public class BankAccountRepository implements BankAccountPersistence {
    private final List<BankAccount> accountList = new ArrayList<>();

    @Override
    public BankAccount saveAccount(BankAccount account) {
        if (getAccountById(account.getId()).isPresent()) {
            throw new AccountAlreadyExistsException(ErrorMessages.ACCOUNT_ALREADY_EXISTS.getMessage());
        }
        accountList.add(account);
        return account;
    }


    @Override
    public BankAccount findAccountById(UUID accountId) {
        return getAccountById(accountId)
                .orElseThrow(() -> new BankAccountException(ErrorMessages.ACCOUNT_NOT_FOUND.getMessage(accountId)));
    }

    @Override
    public void addOperation(UUID accountId, AccountOperation operation) {
        BankAccount account = findAccountById(accountId);
        account.updateBalance(operation.balance());
        account.addOperation(operation);

    }

    private Optional<BankAccount> getAccountById(UUID accountId) {
        return accountList.stream()
                .filter(account -> account.getId().equals(accountId))
                .findFirst();
    }
}
