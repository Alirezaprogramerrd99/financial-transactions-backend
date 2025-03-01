package org.example.financial_transactions.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.example.financial_transactions.model.dto.AccountUpdateRequest;
import org.example.financial_transactions.model.dto.CustomerSummary;
import org.example.financial_transactions.service.IAccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final IAccountService accountService;

    @GetMapping("/find-account-number/{nationalCode}")
    public JsonMessage<String> findAccountNumber(@PathVariable @NotBlank String nationalCode) {
        return new JsonMessage<>(accountService.findAccountNumberByNationalCode(nationalCode));
    }

    @GetMapping("/get-balance/{accountNumber}")
    public JsonMessage<Double> getBalance(@PathVariable String accountNumber) {
        return new JsonMessage<>(accountService.findBalanceByAccountNumber(accountNumber));
    }

    @GetMapping("/get-summary-customer/{accountNumber}")
    public CustomerSummary getSummaryCustomer(@PathVariable String accountNumber) {
        return accountService.getByAccountNumber(accountNumber);
    }

    @PutMapping("/update")
    public void update(@Valid @RequestBody AccountUpdateRequest accountUpdateRequest) {
        accountService.update(accountUpdateRequest);
    }
}
