package org.example.financial_transactions.model.dto;

import org.example.financial_transactions.model.enumutation.AccountType;
import org.example.financial_transactions.model.enumutation.CustomerType;

import java.time.LocalDate;

public record CustomerSummary(String name,
                              String nationalCode,
                              LocalDate establishmentDate,
                              CustomerType customerType,
                              String phoneNumber,
                              String address,
                              String postalCode,
                              AccountType accountType,
                              String accountNumber) {
}
