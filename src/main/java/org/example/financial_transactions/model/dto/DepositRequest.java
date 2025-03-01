package org.example.financial_transactions.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record DepositRequest(
        @NotBlank(message = "destination account must not be null")
        @Pattern(regexp = "^[0-9]{14}$", message = "accountNumber must be 14 digits")
        String destinationAccountNumber,

        @NotNull(message = "amount must not be null")
        @Positive(message = "amount should be positive")
        Double amount) {
}
