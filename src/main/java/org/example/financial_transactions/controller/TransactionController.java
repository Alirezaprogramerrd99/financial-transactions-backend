package org.example.financial_transactions.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.financial_transactions.model.dto.*;
import org.example.financial_transactions.service.ITransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final ITransactionService iTransactionService;

    @PostMapping("/deposit")
    public JsonMessage<Long> increaseBalance(@Valid @RequestBody DepositRequest depositRequest) {
        return new JsonMessage<>(iTransactionService.depositAmount(depositRequest));
    }

    @PostMapping("/withdraw")
    public JsonMessage<Long> withdrawBalance(@Valid @RequestBody WithdrawRequest withdrawRequest) {
        return new JsonMessage<>(iTransactionService.withdrawAmount(withdrawRequest));
    }

    @PostMapping("/transfer")
    public JsonMessage<Long> transferBalance(@Valid @RequestBody TransferRequest transferRequest) {
        return new JsonMessage<>(iTransactionService.transferAmount(transferRequest));
    }

    @GetMapping("/Status-inquiry/{trackingCode}")
    public JsonMessage<StatusInquiryResponse> getStatusInquiry(@PathVariable Long trackingCode) {
        return new JsonMessage<>(iTransactionService.getStatusInquiry(trackingCode));
    }

    @PostMapping("/filter")
    public Page<TransactionSearchResponse> filterTransaction(@Valid @RequestBody TransactionSearch transactionSearch,
                                               @RequestParam(defaultValue = "1")  Integer page,
                                               @RequestParam(defaultValue = "10") Integer size) {
        return iTransactionService.getFilteredTransactions(transactionSearch, PageRequest.of(page, size));
    }
}
