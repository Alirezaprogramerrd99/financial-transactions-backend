package org.example.financial_transactions.validation.customerType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class CustomerTypeValidator implements ConstraintValidator<CustomerTypeValid, String> {

    private static final List<String> VALID_COLUMN_SEARCH = Arrays.asList("real", "legal");

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return name == null || VALID_COLUMN_SEARCH.contains(name.toLowerCase());
    }
}