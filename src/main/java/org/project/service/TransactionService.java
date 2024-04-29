package org.project.service;

import org.project.dto.TransactionListResponse;
import org.project.dto.TransactionRequest;
import org.project.dto.TransactionResponse;

import java.util.UUID;

public interface TransactionService {
    TransactionListResponse getAccountTransactions(UUID account_id);

    TransactionResponse getTransactionById(UUID transactionId);

    TransactionResponse createTransaction(TransactionRequest transactionRequest);
}

