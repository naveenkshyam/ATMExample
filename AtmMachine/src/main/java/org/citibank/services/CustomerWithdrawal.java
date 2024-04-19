package org.citibank.services;

import org.citibank.exceptions.AccountNotFoundException;
import org.citibank.pojos.AtmMachine;
import org.citibank.pojos.BankAccount;

import java.util.Map;
import java.util.Scanner;

public class CustomerWithdrawal extends Thread {

    public void run() {
        System.out.println("Welcome to CitiBank ATM!!");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your card number: ");
        String cardNumber = scanner.nextLine();
        System.out.println("Enter your card pin: ");
        String cardPin = scanner.nextLine();

        try {
            AccountProcessor processor = new AccountProcessor(new AtmMachine());
            processor.isAccountValid(cardNumber, cardPin);
            boolean processed = false;
            while (true) {
                System.out.println("1- Checking, 2 - Savings ");
                int accountType = scanner.nextInt();
                if(accountType != 1 && accountType != 2) {
                    System.out.println("Please indicate savings or checking account");
                    continue;
                }

                System.out.println("1 - Withdrawal, 2 - Balance Inquiry, 3 - End Transaction");
                int option = scanner.nextInt();
                BankAccount account = processor.getBankAccount(accountType);
                account.setCardNumber(cardNumber);
                switch (option) {
                    case 1 -> {
                        System.out.println("Enter amount to withdraw:");
                        int amount = scanner.nextInt();
                        System.out.println("Processing ATM Request...");
                        withdrawMoney(account, processor, amount);
                        processed = true;
                    }
                    case 2 -> processor.printBalance(account);
                    case 3 -> {
                        System.out.println("Transaction ended. Thank you for using our service.");
                        break;
                    }
                    default -> {
                        System.out.println("Invalid option");
                        continue;
                    }
                }
                if (processed) {
                    System.out.println("Do you want to continue? 1 - Yes, 2 - No");
                    int continueOption = scanner.nextInt();
                    if (continueOption == 2) {
                        System.out.println("Transaction ended. Thank you for using our service.");
                        break;
                    }
                }
            }
        } catch (AccountNotFoundException e) {
            System.out.println("Unable to validate account: " + e.getMessage());
        }

    }

    private void withdrawMoney(BankAccount account,
                               AccountProcessor processor, int amount) {
        try {
            Map<Integer, Integer> amountDenomination = processor.withdraw(account, amount);
            processor.dispenseAmount(amountDenomination);
            processor.printBalance(account);
        } catch (Exception e) {
            System.out.println("Unable to process withdrawal: " + e.getMessage());
        }
    }
}
