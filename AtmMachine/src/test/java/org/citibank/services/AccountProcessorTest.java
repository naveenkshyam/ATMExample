package org.citibank.services;

import org.citibank.exceptions.AccountNotFoundException;
import org.citibank.exceptions.ExceededWithdrawalLimitException;
import org.citibank.exceptions.InSufficientBalanceException;
import org.citibank.pojos.AtmMachine;
import org.citibank.pojos.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AccountProcessorTest {

    @Mock
    private AtmMachine atmMachine;

    private AccountProcessor accountProcessor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        accountProcessor = new AccountProcessor(atmMachine);
    }

    @Test
    public void testIsAccountValid() throws AccountNotFoundException {
        assertTrue(accountProcessor.isAccountValid("1234567890", "1234"));
        assertThrows(AccountNotFoundException.class, () -> accountProcessor.isAccountValid("123456789", "123"));
    }

    @Test
    public void testGetBankAccount() {
        assertNotNull(accountProcessor.getBankAccount(1));
        assertNotNull(accountProcessor.getBankAccount(2));
        assertNull(accountProcessor.getBankAccount(3));
    }

    @Test
    public void testWithdraw() throws InSufficientBalanceException, ExceededWithdrawalLimitException {
        BankAccount account = accountProcessor.getBankAccount(1);

        ExceededWithdrawalLimitException exception = assertThrows(ExceededWithdrawalLimitException.class, () -> {
            accountProcessor.withdraw(account, 500);
        });

        assertEquals("Exceeded balance amount in ATM", exception.getMessage());
    }

    @Test
    public void testPrintBalance() {
        BankAccount account = accountProcessor.getBankAccount(1);
        accountProcessor.printBalance(account);
    }

    @Test
    public void testDispenseAmount() {
        Map<Integer, Integer> amountDenomination = new HashMap<>();
        amountDenomination.put(10, 10);
        accountProcessor.dispenseAmount(amountDenomination);
    }
}