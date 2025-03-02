package org.example.financial_transactions.exception;

public class NationalCodeNotFoundException extends RuntimeException {
    public NationalCodeNotFoundException(String nationalCode) {
        super("national code " + nationalCode + "not found!");
    }
}
