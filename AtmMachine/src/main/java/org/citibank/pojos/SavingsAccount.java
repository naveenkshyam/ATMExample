package org.citibank.pojos;

import lombok.Data;

@Data
public class SavingsAccount extends  BankAccount{
    private String accountType;
    public SavingsAccount(String name, int balance, String cardNumber) {
        super(name, balance, cardNumber);
        this.accountType = "Savings";
    }
}
