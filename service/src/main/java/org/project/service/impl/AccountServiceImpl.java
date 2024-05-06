package org.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.dto.response.AccountListResponse;
import org.project.dto.request.AccountRequest;
import org.project.dto.response.AccountResponse;
import org.project.dto.request.TransactionRequest;
import org.project.exception.NotFoundException;
import org.project.exception.TransactionException;
import org.project.model.*;
import org.project.model.enums.Currency;
import org.project.model.enums.TransactionType;
import org.project.repository.AccountRepository;
import org.project.repository.TransactionRepository;
import org.project.repository.UserRepository;
import org.project.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public AccountListResponse getUserAccounts(UUID userId) {
        UserEntity user = getUserById(userId);
        List<AccountEntity> accounts = accountRepository.findAllByUser(user);
        List<AccountResponse> accountResponses = accounts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new AccountListResponse(accountResponses);
    }

    @Override
    public AccountResponse getAccountById(UUID accountId) {
        AccountEntity account = findAccountByIdOrThrow(accountId);
        return convertToDto(account);
    }

    @Override
    @Transactional
    public AccountResponse createAccount(AccountRequest accountRequest) {
        UserEntity user = getUserById(accountRequest.getUserId());
        AccountEntity account = AccountEntity.builder()
                .user(user)
                .balance(accountRequest.getBalance())
                .currency(accountRequest.getCurrency())
                .build();

        TransactionEntity transaction = createTransactionEntity(
                account,
                TransactionType.DEPOSIT,
                accountRequest.getBalance()
        );

        accountRepository.save(account);
        transactionRepository.save(transaction);

        return convertToDto(account);
    }

    @Override
    @Transactional
    public AccountResponse deposit(TransactionRequest transactionRequest) {
        AccountEntity account = findAccountByIdOrThrow(transactionRequest.getAccountId());
        BigDecimal depositAmount = transactionRequest.getAmount();

        accountRepository.updateAccountBalance(account.getId(), depositAmount.negate());

        TransactionEntity transaction = createTransactionEntity(
                account,
                TransactionType.DEPOSIT,
                depositAmount);
        transactionRepository.save(transaction);

        account = findAccountByIdOrThrow(account.getId());

        return convertToDto(account);
    }

    @Override
    @Transactional
    public AccountResponse withdraw(TransactionRequest transactionRequest) {
        AccountEntity account = findAccountByIdOrThrow(transactionRequest.getAccountId());
        BigDecimal withdrawAmount = transactionRequest.getAmount();

        BigDecimal currentBalance = account.getBalance();
        if (currentBalance.compareTo(withdrawAmount) < 0) {
            throw new TransactionException("Insufficient balance for this operation");
        }

        accountRepository.updateAccountBalance(account.getId(), withdrawAmount);

        TransactionEntity transaction = createTransactionEntity(
                account,
                TransactionType.WITHDRAW,
                withdrawAmount);

        transactionRepository.save(transaction);

        account = findAccountByIdOrThrow(account.getId());

        return convertToDto(account);
    }

    @Override
    @Transactional
    public AccountResponse transfer(UUID accountIdTo, TransactionRequest transactionRequest) {
        AccountEntity accountFrom = findAccountByIdOrThrow(transactionRequest.getAccountId());
        AccountEntity accountTo = findAccountByIdOrThrow(accountIdTo);

        BigDecimal transferAmount = transactionRequest.getAmount();

        checkCurrency(accountFrom.getCurrency(), accountTo.getCurrency());
        checkBalance(accountFrom.getBalance(), transferAmount);

        accountRepository.updateAccountBalance(accountFrom.getId(), transferAmount);
        accountRepository.updateAccountBalance(accountTo.getId(), transferAmount.negate());

        TransactionEntity transactionFrom = createTransactionEntity(
                accountFrom,
                TransactionType.TRANSFER,
                transferAmount);
        TransactionEntity transactionTo = createTransactionEntity(
                accountTo,
                TransactionType.DEPOSIT,
                transferAmount);

        transactionRepository.saveAll(List.of(transactionFrom, transactionTo));

        return convertToDto(findAccountByIdOrThrow(accountFrom.getId()));
    }

    @Override
    @Transactional
    public void closeAccount(UUID accountId) {
        AccountEntity account = findAccountByIdOrThrow(accountId);
        BigDecimal currentBalance = account.getBalance();

        if (currentBalance.compareTo(BigDecimal.ZERO) > 0) {
            TransactionEntity transaction = createTransactionEntity(
                    account,
                    TransactionType.WITHDRAW,
                    currentBalance);
            transactionRepository.save(transaction);
        }
        accountRepository.deleteById(accountId);
    }

    private UserEntity getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    private AccountEntity findAccountByIdOrThrow(UUID accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));
    }

    private AccountResponse convertToDto(AccountEntity account) {
        return modelMapper.map(account, AccountResponse.class);
    }

    private void checkCurrency(Currency accountCurrency, Currency paymentCurrency) {
        if (!Objects.equals(accountCurrency, paymentCurrency)) {
            throw new TransactionException("Account currency does not match the payment currency");
        }
    }

    private void checkBalance(BigDecimal balance, BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new TransactionException("Insufficient balance for this operation");
        }
    }

    private TransactionEntity createTransactionEntity(AccountEntity account, TransactionType transactionType, BigDecimal amount) {
        return TransactionEntity.builder()
                .account(account)
                .transactionType(transactionType)
                .amount(amount)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
