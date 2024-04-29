package org.project.service;

import org.project.dto.response.TransactionListResponse;
import org.project.dto.request.TransactionRequest;
import org.project.dto.response.TransactionResponse;

import java.util.UUID;

public interface TransactionService {
    TransactionListResponse getAccountTransactions(UUID account_id);

    TransactionResponse getTransactionById(UUID transactionId);

    TransactionResponse createTransaction(TransactionRequest transactionRequest);
}

