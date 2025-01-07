import com.bank.domain.model.AccountOperation;
import com.bank.domain.model.BankAccount;
import com.bank.domain.model.enums.OperationType;
import com.bank.domain.port.BankAccountPersistence;
import com.bank.domain.service.AccountOperationServiceImpl;
import com.bank.shared.exception.InsufficientFundsException;
import com.bank.shared.exception.InvalidAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


class AccountOperationServiceTest {
    private BankAccountPersistence bankAccountPersistence;
    private AccountOperationServiceImpl accountOperationService;

    @BeforeEach
    void setUp() {
        bankAccountPersistence = mock(BankAccountPersistence.class);
        accountOperationService = new AccountOperationServiceImpl(bankAccountPersistence);
    }

    @Test
    void testDepositValidAmount() {
        UUID accountId = UUID.randomUUID();
        double amount = 50.0;
        BankAccount account = new BankAccount(accountId, 100.0, List.of());

        when(bankAccountPersistence.findAccountById(accountId)).thenReturn(account);

        accountOperationService.deposit(accountId, amount);

        verify(bankAccountPersistence, times(1)).addOperation(eq(accountId), any(AccountOperation.class));
    }

    @Test
    void testDepositInvalidAmountThrowsException() {
        UUID accountId = UUID.randomUUID();
        double amount = -50.0;

        assertThrows(InvalidAmountException.class, () -> accountOperationService.deposit(accountId, amount));
    }

    @Test
    void testWithdrawValidAmount() {
        UUID accountId = UUID.randomUUID();
        double depositAmount = 100.0;
        double withdrawAmount = 50.0;
        BankAccount account = new BankAccount(accountId, depositAmount,List.of());

        when(bankAccountPersistence.findAccountById(accountId)).thenReturn(account);

        accountOperationService.withdraw(accountId, withdrawAmount);

        verify(bankAccountPersistence, times(1)).addOperation(eq(accountId), any(AccountOperation.class));
    }

    @Test
    void testWithdrawInvalidAmountThrowsException() {
        UUID accountId = UUID.randomUUID();
        double withdrawAmount = -50.0;

        assertThrows(InvalidAmountException.class, () -> accountOperationService.withdraw(accountId, withdrawAmount));
    }

    @Test
    void testWithdrawInsufficientFundsThrowsException() {
        UUID accountId = UUID.randomUUID();
        double depositAmount = 30.0;
        double withdrawAmount = 50.0;
        BankAccount account = new BankAccount(accountId, depositAmount,List.of());

        when(bankAccountPersistence.findAccountById(accountId)).thenReturn(account);

        assertThrows(InsufficientFundsException.class, () -> accountOperationService.withdraw(accountId, withdrawAmount));
    }

    @Test
    void testGetOperationsHistory() {
        UUID accountId = UUID.randomUUID();
        BankAccount account = new BankAccount(accountId, 100.0,new ArrayList<>());
        AccountOperation operation1 = new AccountOperation(50.0, LocalDateTime.now(), 150.0, OperationType.DEPOSIT);
        AccountOperation operation2 = new AccountOperation(30.0, LocalDateTime.now(), 120.0, OperationType.WITHDRAWAL);

        account.addOperation(operation1);
        account.addOperation(operation2);

        when(bankAccountPersistence.getOperations(accountId)).thenReturn(account.getAccountOperations());

        var operations = accountOperationService.getOperationsHistory(accountId);

        assertEquals(2, operations.size());
        assertEquals(operation1.amount(), operations.get(0).amount());
        assertEquals(operation2.amount(), operations.get(1).amount());
    }
}
