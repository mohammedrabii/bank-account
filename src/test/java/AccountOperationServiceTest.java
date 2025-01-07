import com.bank.domain.model.AccountOperation;
import com.bank.domain.model.BankAccount;
import com.bank.domain.port.BankAccountPersistence;
import com.bank.domain.service.AccountOperationServiceImpl;
import com.bank.shared.exception.InsufficientFundsException;
import com.bank.shared.exception.InvalidAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


public class AccountOperationServiceTest {
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

}
