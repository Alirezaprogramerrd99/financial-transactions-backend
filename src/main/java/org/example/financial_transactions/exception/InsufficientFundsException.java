package org.example.financial_transactions.exception;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException() {
        super("Insufficient funds for withdrawal");
    }
}
