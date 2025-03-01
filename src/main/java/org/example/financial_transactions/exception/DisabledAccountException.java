package org.example.financial_transactions.exception;

public class DisabledAccountException extends RuntimeException {
    public DisabledAccountException(String accountNumber) {
        super("Account by accountNumber : " + accountNumber + " is disabled");
    }
}
