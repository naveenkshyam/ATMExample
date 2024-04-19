package org.citibank.services;

import org.citibank.constants.AccountTypeEnum;
import org.citibank.exceptions.AccountNotFoundException;
import org.citibank.exceptions.ExceededWithdrawalLimitException;
import org.citibank.exceptions.InSufficientBalanceException;
import org.citibank.pojos.AtmMachine;
import org.citibank.pojos.BankAccount;
import org.citibank.pojos.BankAccountFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccountProcessor {
    private AtmMachine atmMachine;
    private Map<Integer, BankAccount> accounts = new HashMap<>();

    public AccountProcessor(AtmMachine atmMachine){
        this.atmMachine = atmMachine;
        accounts.put(AccountTypeEnum.SAVINGS.getAccountType(),
                BankAccountFactory.createAccount(AccountTypeEnum.SAVINGS.getAccountType(), "John Doe", 1000, "0"));
        accounts.put(AccountTypeEnum.CHECKING.getAccountType(),
                BankAccountFactory.createAccount(AccountTypeEnum.CHECKING.getAccountType(), "John Doe", 1000, "0"));
    }
    public boolean isAccountValid(String cardNumber, String pin) throws AccountNotFoundException {
        if(cardNumber.length() == 10 && pin.length() == 4) {
            return true;
        }else {
            throw new AccountNotFoundException("Invalid card number or pin");
        }
    }

    public BankAccount getBankAccount(int accountType){
        return accounts.get(accountType);
    }

    public synchronized Map<Integer, Integer> withdraw(BankAccount account, int amount) throws InSufficientBalanceException, ExceededWithdrawalLimitException {
        Map<Integer, Integer> withdrawalDenominations = new HashMap<>();
        Map<Integer, Integer> atmDenominations = atmMachine.getAtmDenominations();
        validateWithdrawalAmount(amount, account.getBalance());
        int remainingWithdraws = amount;
        for(int denomination : atmDenominations.keySet()) {
            int availableNotes = atmDenominations.get(denomination);

            int notesToWithdraw = Math.min(availableNotes, remainingWithdraws / denomination);
            if(notesToWithdraw >0) {
                withdrawalDenominations.put(denomination, notesToWithdraw);
                atmDenominations.put(denomination, atmDenominations.get(denomination) - notesToWithdraw);
                remainingWithdraws = remainingWithdraws - denomination * notesToWithdraw;
                atmMachine.withdrawAmount( denomination * notesToWithdraw);
            }
            if(remainingWithdraws == 0) {
                break;
            }
        }

        account.setBalance(account.getBalance() - amount);
        return withdrawalDenominations;
    }

    private void validateWithdrawalAmount(int amount, int balance) throws InSufficientBalanceException, ExceededWithdrawalLimitException {
        if(amount <= 0 || amount % 10 != 0) {
            throw new IllegalArgumentException("Invalid withdrawal amount");
        }else if(amount > balance) {
            throw new InSufficientBalanceException("Insufficient withdraw amount in your account");
        }else if(amount > atmMachine.getAtmBalance()) {
            throw new ExceededWithdrawalLimitException("Exceeded balance amount in ATM");
        }
    }

    public void printBalance(BankAccount account) {
        System.out.println("Printing balance for account number: "+ getAccountNumberMasked(account));
        System.out.println("Transaction Id: " + UUID.randomUUID().toString());
        System.out.println("Available Balance: " + account.getBalance());

    }

    private static String getAccountNumberMasked(BankAccount account) {
        return account.getCardNumber().replaceAll("\\w(?=\\w{4})", "*");
    }

    public void dispenseAmount(Map<Integer, Integer> amountDenomination) {
        for(int denomination: amountDenomination.keySet()) {
            int count = amountDenomination.get(denomination);
            System.out.println("Dispensing " + count + " notes of " + denomination);
        }
    }
}
