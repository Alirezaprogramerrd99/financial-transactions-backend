package org.example.financial_transactions.service;

import org.example.financial_transactions.model.Account;
import org.example.financial_transactions.model.dto.AccountUpdateRequest;
import org.example.financial_transactions.model.dto.CustomerSummary;

public interface IAccountService {
    Account createAccount();

    String findAccountNumberByNationalCode(String nationalCode);

    Double findBalanceByAccountNumber(String accountNumber);

    Boolean existByNationalCode(String nationalCode);

    Boolean existsByAccountNumber(String accountNumber);

    CustomerSummary getByAccountNumber(String accountNumber);

    Account findById(Integer id);

    void update(AccountUpdateRequest accountUpdateRequest);

    Account findByAccountNumber(String accountNumber);

    void pureSave(Account account);
}
