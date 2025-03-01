package org.example.financial_transactions.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(Long trackingCode) {
        super("transaction not found for trackingCode: " + trackingCode);
    }
}
