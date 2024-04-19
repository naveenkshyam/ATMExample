package org.citibank.pojos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public abstract class BankAccount {
    private String branch;
    private String accountHolder;
    private Date accountOpened;
    private int withdrawsLeft;
    private int balance;
    private String cardNumber;
    private int pin;

    public BankAccount(String name, int initialBalance, String cardNumber) {
        this.balance = initialBalance;
        this.accountHolder = name;
        this.accountOpened = new Date();
        this.cardNumber = cardNumber;
    }

}
