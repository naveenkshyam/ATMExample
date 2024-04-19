package org.citibank.exceptions;

public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String message) {
        super(message);
    }
    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
