package org.citibank.pojos;

public class BankAccountFactory {
    public static BankAccount createAccount(int accountType, String name, int balance, String cardNumber) {
        return switch (accountType) {
            case 1 -> new CheckingAccount(name, balance, cardNumber);
            case 2 -> new SavingsAccount(name, balance, cardNumber);
            default -> {
                System.out.println("Please indicate savings or checking account");
                yield null;
            }
        };
    }
}
