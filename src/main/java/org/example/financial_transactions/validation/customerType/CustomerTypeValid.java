package org.example.financial_transactions.validation.customerType;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CustomerTypeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomerTypeValid {
    String message() default
            "please enter valid Customer Type(REAL|LEGAL)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}