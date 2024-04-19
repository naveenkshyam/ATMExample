package org.citibank;

import org.citibank.services.CustomerWithdrawal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AtmApplication {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new CustomerWithdrawal());
       // executor.execute(new CustomerWithdrawal());
        executor.shutdown();

    }

}
