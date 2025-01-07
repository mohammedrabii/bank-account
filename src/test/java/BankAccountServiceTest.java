import com.bank.api.model.BankAccountDto;
import com.bank.domain.model.BankAccount;
import com.bank.domain.port.BankAccountPersistence;
import com.bank.domain.service.BankAccountServiceImpl;
import com.bank.shared.exception.AccountAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class BankAccountServiceTest {

    private BankAccountPersistence bankAccountPersistence;
    private BankAccountServiceImpl bankAccountService;

    @BeforeEach
    void setUp() {
        bankAccountPersistence = mock(BankAccountPersistence.class);
        bankAccountService = new BankAccountServiceImpl(bankAccountPersistence);
    }

    @Test
    void testCreateAccount() {
        BankAccount newAccount = BankAccount.builder()
                .id(UUID.randomUUID())
                .balance(0.0)
                .build();

        when(bankAccountPersistence.findAccountById(any(UUID.class))).thenReturn(null);
        when(bankAccountPersistence.saveAccount(any(BankAccount.class))).thenReturn(newAccount);

        BankAccountDto result = bankAccountService.createAccount();

        assertNotNull(result);
        assertEquals(0.0, result.balance());
        assertNotNull(result.id());
        verify(bankAccountPersistence, times(1)).saveAccount(any(BankAccount.class));
    }

    @Test
    void testCreateAccountWithExistingAccountThrowsException() {
        BankAccount existingAccount = BankAccount.builder()
                .id(UUID.randomUUID())
                .balance(100.0)
                .build();

        when(bankAccountPersistence.findAccountById(any(UUID.class))).thenReturn(existingAccount);

        assertThrows(AccountAlreadyExistsException.class, () -> bankAccountService.createAccount());
    }


}