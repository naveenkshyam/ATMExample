package org.citibank.exceptions;

public class InSufficientBalanceException extends Exception {
    public InSufficientBalanceException(String message) {
        super(message);
    }
    public InSufficientBalanceException(String message, Throwable cause) {
        super(message, cause);
    }

}
