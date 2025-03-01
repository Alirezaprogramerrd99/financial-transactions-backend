package org.example.financial_transactions.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ErrorDetails {
    private LocalDateTime localDateTime;
    private String massage;
}