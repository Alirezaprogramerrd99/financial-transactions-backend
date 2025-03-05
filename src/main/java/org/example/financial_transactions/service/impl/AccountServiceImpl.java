package org.example.financial_transactions.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.financial_transactions.dao.AccountRepository;
import org.example.financial_transactions.exception.AccountNotFoundException;
import org.example.financial_transactions.exception.EntityNotFoundException;
import org.example.financial_transactions.exception.IdNotFoundException;
import org.example.financial_transactions.model.Account;
import org.example.financial_transactions.model.Admin;
import org.example.financial_transactions.model.History;
import org.example.financial_transactions.model.dto.AccountUpdateRequest;
import org.example.financial_transactions.model.dto.CustomerSummary;
import org.example.financial_transactions.model.enumutation.AccountType;
import org.example.financial_transactions.service.IAccountService;
import org.example.financial_transactions.service.IHistoryService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final AccountRepository repository;
    private final IHistoryService iHistoryService;
    private static final AtomicLong counter = new AtomicLong(1);


    @Override
    public Account createAccount() {
        Account account = new Account();
        String s = generateUniqueAccountNumber();
        account.setAccountNumber(s);
        account.setAccountType(AccountType.ACTIVE);
        account.setBalance(0D);
        return repository.save(account);
    }

    @Override
    public String findAccountNumberByNationalCode(String nationalCode) {
        if (!existByNationalCode(nationalCode))
            throw new EntityNotFoundException(nationalCode);
        return repository.findAccountNumberByNationalCode(nationalCode);
    }

    @Override
    public Double findBalanceByAccountNumber(String accountNumber) {
        if (!existsByAccountNumber(accountNumber))
            throw new AccountNotFoundException(accountNumber);
        return repository.findBalanceByAccountNumber(accountNumber);
    }

    @Override
    public Boolean existByNationalCode(String nationalCode) {
        return repository.existsByNationalCode(nationalCode);
    }

    @Override
    public Boolean existsByAccountNumber(String accountNumber) {
        return repository.existByAccountNumber(accountNumber);
    }

    @Override
    public CustomerSummary getByAccountNumber(String accountNumber) {
        Optional<CustomerSummary> byAccountNumber = repository.getByAccountNumber(accountNumber);
        if (byAccountNumber.isEmpty())
            throw new AccountNotFoundException(accountNumber);
        return byAccountNumber.get();
    }

    @Override
    public Account findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
    }

    @Override
    @Transactional
    public void update(AccountUpdateRequest accountUpdateRequest) {
        Account prevAccount = findById(accountUpdateRequest.id());
        StringBuilder description = new StringBuilder();
        Optional.ofNullable(accountUpdateRequest.accountType()).ifPresent(newType -> {
            if (!newType.equalsIgnoreCase(prevAccount.getAccountType().name())) {  // .name(): Converts the AccountType enum to its String representation
                description.append("account type: ").append(newType);
                prevAccount.setAccountType(AccountType.valueOf(newType)); // This converts the String to the corresponding AccountType enum.
            }
        });
        if (!description.isEmpty()) {
            description.append(" successfully updated");
            saveHistory(String.valueOf(description), prevAccount);
        }
        repository.save(prevAccount);
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        return repository.findByAccountNumber(accountNumber).orElseThrow(() -> new AccountNotFoundException(accountNumber));
    }

    @Override
    public void pureSave(Account account) {
        repository.save(account);
    }

    public void saveHistory(String description, Account account) {
        Admin principal = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        History history = new History(principal.getUsername(), LocalDateTime.now(), description, account, null);
        iHistoryService.save(history);
    }

    public String generateUniqueAccountNumber() {
        long uniqueNumber = counter.getAndIncrement();
        return String.format("%014d", uniqueNumber);
    } // generates a number(in string format) and pads it with necessary zeros.
}
