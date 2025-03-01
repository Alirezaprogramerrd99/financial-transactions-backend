package org.example.financial_transactions.model.dto;

import org.example.financial_transactions.validation.AccountType.AccountTypeValid;

public record AccountUpdateRequest(Integer id,
                                   @AccountTypeValid String accountType) {
}
