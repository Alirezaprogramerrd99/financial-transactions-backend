package org.example.financial_transactions.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.financial_transactions.dao.TransactionRepository;
import org.example.financial_transactions.exception.DisabledAccountException;
import org.example.financial_transactions.exception.InsufficientFundsException;
import org.example.financial_transactions.exception.TransactionNotFoundException;
import org.example.financial_transactions.model.Account;
import org.example.financial_transactions.model.Transaction;
import org.example.financial_transactions.model.TransferFeeConfig;
import org.example.financial_transactions.model.dto.*;
import org.example.financial_transactions.model.enumutation.AccountType;
import org.example.financial_transactions.model.enumutation.TransactionStatus;
import org.example.financial_transactions.model.enumutation.TransactionType;
import org.example.financial_transactions.service.IAccountService;
import org.example.financial_transactions.service.ITransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements ITransactionService {
    private final TransactionRepository repository;
    private final IAccountService iAccountService;
    private final TransferFeeConfig transferFeeConfig;

    @Override
    @Transactional
    public long transferAmount(TransferRequest transferRequest) {
        Account source = iAccountService.findByAccountNumber(transferRequest.sourceAccountNumber());
        Account destination = iAccountService.findByAccountNumber(transferRequest.destinationAccountNumber());
        checkAccountStatus(source);
        checkAccountStatus(destination);
        transferAmount(source, destination, transferRequest.amount());
        long trackingCode = generateTrackingCode();
        Transaction transaction = initializationTransaction(destination, source, TransactionType.TRANSFER, trackingCode, transferRequest.amount());
        repository.save(transaction);
        return trackingCode;
    }

    @Override
    @Transactional
    public long depositAmount(DepositRequest depositRequest) {
        Account destination = iAccountService.findByAccountNumber(depositRequest.destinationAccountNumber());
        checkAccountStatus(destination);
        increaseBalance(destination, depositRequest.amount());
        long trackingCode = generateTrackingCode();
        Transaction transaction = initializationTransaction(destination, null, TransactionType.DEPOSIT, trackingCode, depositRequest.amount());
        repository.save(transaction);
        return trackingCode;
    }

    private Transaction initializationTransaction(Account destinationAccount, Account sourceAccount, TransactionType transactionType, Long trackingCode, Double amount) {
        Transaction transaction = new Transaction();
        transaction.setDestinationAccount(destinationAccount);
        transaction.setTransactionType(transactionType);
        transaction.setTrackingCode(trackingCode);
        transaction.setAmount(amount);
        transaction.setTransactionStatus(TransactionStatus.CONFIRM);
        return transaction;
    }

    @Override
    @Transactional
    public long withdrawAmount(WithdrawRequest withdrawRequest) {
        Account source = iAccountService.findByAccountNumber(withdrawRequest.sourceAccountNumber());
        checkAccountStatus(source);
        processTransfer(source, withdrawRequest.amount());
        long trackingCode = generateTrackingCode();
        Transaction transaction = initializationTransaction(source, null, TransactionType.WITHDRAW, trackingCode, withdrawRequest.amount());
        repository.save(transaction);
        return trackingCode;
    }

    @Override
    public StatusInquiryResponse getStatusInquiry(Long trackingCode) {
        Transaction transaction = findByTrackingCode(trackingCode);
        return new StatusInquiryResponse(transaction.getId(), transaction.getCreationDate());
    }

    @Override
    public Transaction findByTrackingCode(Long trackingCode) {
        return repository.findByTrackingCode(trackingCode).orElseThrow(() -> new TransactionNotFoundException(trackingCode));
    }

    private long generateTrackingCode() {
        Random random = new Random();
        return random.nextLong(100000000);

    }

    private void checkAccountStatus(Account account) {
        boolean accountType = account.getAccountType().equals(AccountType.ACTIVE);
        if (!accountType)
            throw new DisabledAccountException(account.getAccountNumber());
    }

    private void increaseBalance(Account account, Double amount) {
        Double balance = account.getBalance();
        balance += amount;
        account.setBalance(balance);
        iAccountService.pureSave(account);
    }

    private void processTransfer(Account account, Double amount) {
        double transferFee = calculateTransferFee(amount);
        if (amount + transferFee > account.getBalance())
            throw new InsufficientFundsException();
        double balance = account.getBalance();
        balance -= amount + transferFee;
        if (balance < 0)
            throw new InsufficientFundsException();
        saveTransferFee(transferFee);
        account.setBalance(balance);
        iAccountService.pureSave(account);
    }

    private void transferAmount(Account sourceAccount, Account destinationAccount, Double amount) {
        processTransfer(sourceAccount, amount);
        Double destinationBalance = destinationAccount.getBalance();
        destinationBalance += amount;
        destinationAccount.setBalance(destinationBalance);
        iAccountService.pureSave(destinationAccount);
    }

    private double calculateTransferFee(Double amount) {
        double calculatedFee = amount * (transferFeeConfig.getPercentage() / 1000);
        if (calculatedFee < transferFeeConfig.getFloor()) {
            return transferFeeConfig.getFloor();
        } else if (calculatedFee > transferFeeConfig.getCeiling()) {
            return transferFeeConfig.getCeiling();
        } else {
            return calculatedFee;
        }
    }

    private void saveTransferFee(double transferFee) {
        Account bankAccount = iAccountService.findByAccountNumber("11111111111111");
        Double balance = bankAccount.getBalance();
        balance += transferFee;
        bankAccount.setBalance(balance);
        iAccountService.pureSave(bankAccount);
    }

    @Override
    public Page<TransactionSearchResponse> getFilteredTransactions(TransactionSearch transactionSearch, Pageable pageable) {
        Page<Transaction> transactions = repository.findAll(transactionSearch, pageable);
        return transactions.map(transaction -> new TransactionSearchResponse(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getCreationDate(),
                transaction.getTrackingCode()
        ));
    }
}
