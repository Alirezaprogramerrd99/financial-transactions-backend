package org.example.financial_transactions.model.dto;

import jakarta.validation.constraints.Pattern;
import org.example.financial_transactions.validation.customerType.CustomerTypeValid;
import org.example.financial_transactions.validation.localDate.ValidLocalDate;

import java.time.LocalDate;

public record CustomerUpdateRequest(Integer id,
                                    @Pattern(regexp = "^[A-Za-z]{3,29}$", message = "Name must be between 3 and 29 letters")
                                    String name,

                                    @Pattern(regexp = "^\\d{10}$", message = "National code must be exactly 10 digits")
                                    String nationalCode,

                                    @ValidLocalDate
                                    LocalDate establishmentDate,

                                    @CustomerTypeValid
                                    String customerType,

                                    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be between 10 to 15 digits, optionally prefixed with '+'")
                                    String phoneNumber,

                                    String address,

                                    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Postal code must be 5 digits, optionally followed by a hyphen and 4 digits")
                                    String postalCode) {
}
