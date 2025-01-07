package com.bank.domain.service;

import com.bank.api.model.AccountOperationDto;
import com.bank.domain.incomingservice.AccountOperationService;
import com.bank.domain.model.AccountOperation;
import com.bank.domain.model.BankAccount;
import com.bank.domain.model.enums.OperationType;
import com.bank.domain.port.BankAccountPersistence;
import com.bank.shared.exception.ErrorMessages;
import com.bank.shared.exception.InsufficientFundsException;
import com.bank.shared.exception.InvalidAmountException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class AccountOperationServiceImpl implements AccountOperationService {

    private final BankAccountPersistence bankAccountPersistence;

    public AccountOperationServiceImpl(BankAccountPersistence bankAccountPersistence) {
        this.bankAccountPersistence = bankAccountPersistence;
    }

    /**
     * Effectue un dépôt sur un compte bancaire.
     *
     * @param accountId identifiant du compte.
     * @param amount    montant du dépôt.
     * @throws InvalidAmountException si le montant est négatif, nul .
     */
    public void deposit(UUID accountId, double amount) {
        validatePositiveAmount(amount, ErrorMessages.DEPOSIT_AMOUNT_NEGATIVE);

        BankAccount account = bankAccountPersistence.findAccountById(accountId);
        double newBalance = account.getBalance() + amount;

        AccountOperation operation = new AccountOperation(
                amount,
                LocalDateTime.now(),
                newBalance,
                OperationType.DEPOSIT
        );

        bankAccountPersistence.addOperation(accountId, operation);
    }

    /**
     * Effectue un retrait depuis un compte bancaire.
     *
     * @param accountId identifiant du compte.
     * @param amount    montant du retrait.
     * @throws InsufficientFundsException si le solde est insuffisant.
     * @throws InvalidAmountException     si le montant est négatif, nul .
     */
    public void withdraw(UUID accountId, double amount) {
        validatePositiveAmount(amount, ErrorMessages.WITHDRAWAL_AMOUNT_NEGATIVE);
        BankAccount account = bankAccountPersistence.findAccountById(accountId);
        if (account.getBalance() < amount)
            throw new InsufficientFundsException(ErrorMessages.INSUFFICIENT_FUNDS.getMessage());

        double newBalance = account.getBalance() - amount;

        AccountOperation operation = new AccountOperation(
                -amount,
                LocalDateTime.now(),
                newBalance,
                OperationType.WITHDRAWAL
        );

        bankAccountPersistence.addOperation(accountId, operation);
    }

    /**
     * Récupère l'historique des opérations pour un compte donné.
     *
     * @param accountId identifiant du compte.
     * @return la liste des opérations .
     */
    public List<AccountOperationDto> getOperationsHistory(UUID accountId) {
        return bankAccountPersistence.getOperations(accountId).stream()
                .map(operation -> AccountOperationDto.builder()
                        .operationDate(operation.operationDate())
                        .type(operation.type())
                        .amount(operation.amount())
                        .balance(operation.balance())
                        .build())
                .toList();
    }

    private void validatePositiveAmount(double amount, ErrorMessages errorMessage) {
        if (amount <= 0) {
            throw new InvalidAmountException(errorMessage.getMessage());
        }
    }

}
