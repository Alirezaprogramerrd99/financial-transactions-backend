package org.example.financial_transactions.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.example.financial_transactions.validation.customerType.CustomerTypeValid;
import org.example.financial_transactions.validation.localDate.ValidLocalDate;

import java.time.LocalDate;

public record CustomerRequest(
        @NotBlank(message = "name must not be null")
        @Pattern(regexp = "^[A-Za-z]{3,29}$", message = "Name must be between 3 and 29 letters")
        String name,

        @NotBlank(message = "nationalCode must not be null")
        @Pattern(regexp = "^\\d{10}$", message = "National code must be exactly 10 digits")
        String nationalCode,

        @NotNull(message = "Establishment date cannot be null")
        @ValidLocalDate
        LocalDate establishmentDate,

        @NotNull(message = "customerType cannot be null")
        @CustomerTypeValid
        String customerType,

        @NotBlank(message = "phone number must not be null")
        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be between 10 to 15 digits, optionally prefixed with '+'")
        String phoneNumber,

        @NotBlank(message = "address must not be null")
        @Pattern(regexp = "^.+$", message = "Address cannot be empty")
        String address,

        @NotBlank(message = "postalCode must not be null")
        @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Postal code must be 5 digits, optionally followed by a hyphen and 4 digits")
        String postalCode) {
}
