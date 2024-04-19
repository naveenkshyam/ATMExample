package org.citibank.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckingAccount extends BankAccount {
    private String accountType;
    public CheckingAccount(String name, int balance, String cardNumber) {
        super(name, balance, cardNumber);
        this.accountType = "Checking";
    }
}
