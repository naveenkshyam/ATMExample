package org.citibank.exceptions;

public class ExceededWithdrawalLimitException extends Exception {
    public ExceededWithdrawalLimitException(String message) {
        super(message);
    }
    public ExceededWithdrawalLimitException(String message, Throwable cause) {
        super(message, cause);
    }
}
