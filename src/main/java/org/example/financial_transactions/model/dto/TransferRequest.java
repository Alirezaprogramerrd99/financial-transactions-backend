package org.example.financial_transactions.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;

public record TransferRequest(
        @NotBlank(message = "source account must not be null")
        @Pattern(regexp = "^[0-9]{14}$", message = "accountNumber must be 14 digits")
        String sourceAccountNumber,

        @NotBlank(message = "destination account must not be null")
        @Pattern(regexp = "^[0-9]{14}$", message = "accountNumber must be 14 digits")
        String destinationAccountNumber,

        @NotNull(message = "amount must not be null")
        @Positive(message = "amount should be positive")
        Double amount) {


    @JsonIgnore
    @AssertTrue(message = "the source and destination account numbers cannot be the same")
    public boolean isEqualAccountNumber() {
        return !sourceAccountNumber.equals(destinationAccountNumber);
    }
}
