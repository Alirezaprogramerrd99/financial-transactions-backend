package org.example.financial_transactions.validation.AccountType;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AccountTypeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccountTypeValid {
    String message() default
            "please enter valid Account Type(ACTIVE|INACTIVE|BLOCKED)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
