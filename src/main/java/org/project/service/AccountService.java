package org.project.service;

import org.project.dto.response.AccountListResponse;
import org.project.dto.request.AccountRequest;
import org.project.dto.response.AccountResponse;
import org.project.dto.request.TransactionRequest;

import java.util.UUID;

public interface AccountService {

    AccountResponse createAccount(AccountRequest accountRequest);

    AccountListResponse getUserAccounts(UUID uuid);

    AccountResponse getAccountById(UUID accountId);

    AccountResponse deposit(TransactionRequest transactionRequest);

    AccountResponse withdraw(TransactionRequest transactionRequest);

    AccountResponse transfer(UUID accountIdTo, TransactionRequest transactionRequest);

    boolean closeAccount(UUID accountId);
}





