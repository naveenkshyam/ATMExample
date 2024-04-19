package org.citibank.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
@AllArgsConstructor
public class AtmMachine {
    private String atmId;
    private String atmStatus;
    private AtmMachineDetails atmMachineDetails;
    private HashMap<Integer, Integer> atmDenominations;
    private Integer atmBalance;

    public AtmMachine() {

        this.atmId = "ATM001";
        this.atmStatus = "ACTIVE";
        atmMachineDetails = AtmMachineDetails.builder()
                .location("New York")
                .build();
        atmDenominations = new HashMap<>();
        atmDenominations.put(10, 100);
        atmDenominations.put(20, 50);
        atmDenominations.put(50, 40);
        atmBalance = 4000;
    }


    public Integer withdrawAmount(int amount) {
        if (amount > atmBalance) {
            System.out.println("Insufficient funds in ATM");
            return 0;
        }
        atmBalance -= amount;
        return amount;
    }
}
