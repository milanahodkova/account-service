package org.project.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.project.dto.request.AccountRequest;
import org.project.dto.request.TransactionRequest;
import org.project.dto.response.AccountListResponse;
import org.project.dto.response.AccountResponse;
import org.project.exception.NotFoundException;
import org.project.exception.TransactionException;
import org.project.model.AccountEntity;
import org.project.model.TransactionEntity;
import org.project.model.UserEntity;
import org.project.model.enums.Currency;
import org.project.repository.AccountRepository;
import org.project.repository.TransactionRepository;
import org.project.repository.UserRepository;
import org.project.util.DataUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AccountServiceImpl accountService;


    @Test
    void getUserAccounts_ShouldReturnUserAccounts_WhenUserExists() {
        List<AccountEntity> accountEntities = new ArrayList<>();
        AccountEntity accountEntity = DataUtils.getAccountEntity();
        UserEntity userEntity = DataUtils.getUserEntity();
        UUID userId = UUID.randomUUID();
        AccountResponse accountResponse = DataUtils.getAccountResponse();
        accountEntities.add(accountEntity);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(accountRepository.findAllByUser(userEntity)).thenReturn(accountEntities);
        when(modelMapper.map(accountEntity, AccountResponse.class)).thenReturn(accountResponse);

        AccountListResponse result = accountService.getUserAccounts(userId);

        assertNotNull(result);
        assertEquals(1, result.getAccountResponseList().size());
        assertEquals(accountResponse, result.getAccountResponseList().getFirst());

        verify(userRepository, times(1)).findById(userId);
        verify(accountRepository, times(1)).findAllByUser(userEntity);
    }

    @Test
    void getUserAccounts_ShouldThrowNotFoundException_WhenUserDoesNotExist() {
        UUID userId = DataUtils.getUserId();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.getUserAccounts(userId));

        verify(userRepository, times(1)).findById(userId);
        verify(accountRepository, never()).findAllByUser(any());
    }

    @Test
    void getAccountById_ShouldReturnAccount_WhenAccountExists() {
        UUID accountId = DataUtils.getAccountId();
        AccountEntity accountEntity = DataUtils.getAccountEntity();
        AccountResponse accountResponse = DataUtils.getAccountResponse();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountEntity));
        when(modelMapper.map(accountEntity, AccountResponse.class)).thenReturn(accountResponse);

        AccountResponse result = accountService.getAccountById(accountId);

        assertNotNull(result);
        assertEquals(accountResponse, result);

        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    void getAccountById_ShouldThrowNotFoundException_WhenAccountDoesNotExist() {
        UUID accountId = DataUtils.getAccountId();
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.getAccountById(accountId));

        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    void createAccount_ShouldCreateAccount_WhenUserExists() {
        UUID userId = DataUtils.getUserId();
        UserEntity userEntity = DataUtils.getUserEntity();
        AccountRequest accountRequest = DataUtils.getAccountRequest();
        AccountResponse accountResponse = DataUtils.getAccountResponse();
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(accountRepository.save(any(AccountEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(transactionRepository.save(any(TransactionEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(modelMapper.map(any(AccountEntity.class), eq(AccountResponse.class))).thenReturn(accountResponse);

        AccountResponse result = accountService.createAccount(accountRequest);

        assertNotNull(result);
        assertEquals(accountResponse, result);

        verify(userRepository, times(1)).findById(userId);
        verify(accountRepository, times(1)).save(any(AccountEntity.class));
        verify(transactionRepository, times(1)).save(any(TransactionEntity.class));
    }


    @Test
    void createAccount_ShouldThrowNotFoundException_WhenUserDoesNotExist() {
        UUID userId = DataUtils.getUserId();
        AccountRequest accountRequest = DataUtils.getAccountRequest();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.createAccount(accountRequest));

        verify(userRepository, times(1)).findById(userId);
        verify(accountRepository, never()).save(any(AccountEntity.class));
        verify(transactionRepository, never()).save(any(TransactionEntity.class));
    }

    @Test
    void deposit_ShouldDepositAmount_WhenAccountExists() {
        UUID accountId = DataUtils.getAccountId();
        AccountEntity accountEntity = DataUtils.getAccountEntity();
        TransactionRequest transactionRequest = DataUtils.getTransactionRequest();
        AccountResponse accountResponse = DataUtils.getAccountResponse();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountEntity));
        when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(new TransactionEntity());
        when(modelMapper.map(accountEntity, AccountResponse.class)).thenReturn(accountResponse);

        AccountResponse result = accountService.deposit(transactionRequest);

        assertNotNull(result);
        assertEquals(accountResponse, result);

        verify(accountRepository, times(1)).updateAccountBalance(accountId,
                transactionRequest.getAmount().negate());
        verify(transactionRepository, times(1)).save(any(TransactionEntity.class));
    }

    @Test
    void deposit_ShouldThrowNotFoundException_WhenAccountDoesNotExist() {
        UUID accountId = DataUtils.getAccountId();
        TransactionRequest transactionRequest = DataUtils.getTransactionRequest();
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.deposit(transactionRequest));

        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, never()).updateAccountBalance(any(), any());
        verify(transactionRepository, never()).save(any(TransactionEntity.class));
    }

    @Test
    void withdraw_ShouldWithdrawAmount_WhenAccountExistsAndBalanceIsSufficient() {
        UUID accountId = DataUtils.getAccountId();
        AccountEntity accountEntity = DataUtils.getAccountEntity();
        TransactionRequest transactionRequest = DataUtils.getTransactionRequest();
        AccountResponse accountResponse = DataUtils.getAccountResponse();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountEntity));
        when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(new TransactionEntity());
        when(modelMapper.map(accountEntity, AccountResponse.class)).thenReturn(accountResponse);

        AccountResponse result = accountService.withdraw(transactionRequest);

        assertNotNull(result);
        assertEquals(accountResponse, result);

        verify(accountRepository, times(1)).updateAccountBalance(accountId,
                transactionRequest.getAmount());
        verify(transactionRepository, times(1)).save(any(TransactionEntity.class));
    }

    @Test
    void withdraw_ShouldThrowNotFoundException_WhenAccountDoesNotExist() {
        UUID accountId = DataUtils.getAccountId();
        TransactionRequest transactionRequest = DataUtils.getTransactionRequest();
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.withdraw(transactionRequest));

        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, never()).updateAccountBalance(any(), any());
        verify(transactionRepository, never()).save(any(TransactionEntity.class));
    }

    @Test
    void withdraw_ShouldThrowTransactionException_WhenBalanceIsInsufficient() {
        TransactionRequest transactionRequest = DataUtils.getTransactionRequest();
        AccountEntity accountEntity = DataUtils.getAccountEntity();
        UUID accountId = DataUtils.getAccountId();
        BigDecimal withdrawAmount = BigDecimal.valueOf(1500);
        transactionRequest.setAmount(withdrawAmount);
        accountEntity.setBalance(BigDecimal.valueOf(1000));

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountEntity));

        assertThrows(TransactionException.class, () -> accountService.withdraw(transactionRequest));

        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, never()).updateAccountBalance(any(), any());
        verify(transactionRepository, never()).save(any(TransactionEntity.class));
    }

    @Test
    void transfer_ShouldTransferAmount_WhenBothAccountsExistAndBalanceIsSufficient() {
        UUID accountId = DataUtils.getAccountId();
        UserEntity userEntity = DataUtils.getUserEntity();
        AccountEntity accountEntity = DataUtils.getAccountEntity();
        AccountResponse accountResponse = DataUtils.getAccountResponse();
        TransactionRequest transactionRequest = DataUtils.getTransactionRequest();
        UUID accountIdTo = UUID.randomUUID();
        AccountEntity accountTo = AccountEntity.builder()
                .id(accountIdTo)
                .user(userEntity)
                .balance(BigDecimal.valueOf(2000))
                .currency(Currency.USD)
                .build();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountEntity));
        when(accountRepository.findById(accountIdTo)).thenReturn(Optional.of(accountTo));
        when(transactionRepository.saveAll(anyList())).thenReturn(new ArrayList<>());
        when(modelMapper.map(accountEntity, AccountResponse.class)).thenReturn(accountResponse);

        AccountResponse result = accountService.transfer(accountIdTo, transactionRequest);

        assertNotNull(result);
        assertEquals(accountResponse, result);

        verify(accountRepository, times(1)).findById(accountIdTo);
        verify(accountRepository, times(1))
                .updateAccountBalance(accountId, transactionRequest.getAmount());
        verify(accountRepository, times(1))
                .updateAccountBalance(accountIdTo, transactionRequest.getAmount().negate());
        verify(transactionRepository, times(1)).saveAll(anyList());
    }

    @Test
    void transfer_ShouldThrowNotFoundException_WhenAccountFromDoesNotExist() {
        UUID accountId = DataUtils.getAccountId();
        TransactionRequest transactionRequest = DataUtils.getTransactionRequest();
        UUID accountIdTo = UUID.randomUUID();
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.transfer(accountIdTo, transactionRequest));

        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, never()).findById(accountIdTo);
        verify(accountRepository, never()).updateAccountBalance(any(), any());
        verify(transactionRepository, never()).saveAll(anyList());
    }

    @Test
    void transfer_ShouldThrowNotFoundException_WhenAccountToDoesNotExist() {
        UUID accountId = DataUtils.getAccountId();
        AccountEntity accountEntity = DataUtils.getAccountEntity();
        TransactionRequest transactionRequest = DataUtils.getTransactionRequest();
        UUID accountIdTo = UUID.randomUUID();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountEntity));
        when(accountRepository.findById(accountIdTo)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.transfer(accountIdTo, transactionRequest));

        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, times(1)).findById(accountIdTo);
        verify(accountRepository, never()).updateAccountBalance(any(), any());
        verify(transactionRepository, never()).saveAll(anyList());
    }

    @Test
    void transfer_ShouldThrowTransactionException_WhenBalanceIsInsufficient() {
        UserEntity userEntity = DataUtils.getUserEntity();
        TransactionRequest transactionRequest = DataUtils.getTransactionRequest();
        UUID accountId = DataUtils.getAccountId();
        AccountEntity accountEntity = DataUtils.getAccountEntity();
        UUID accountIdTo = UUID.randomUUID();
        AccountEntity accountTo = AccountEntity.builder()
                .id(accountIdTo)
                .user(userEntity)
                .balance(BigDecimal.valueOf(2000))
                .currency(Currency.USD)
                .build();

        BigDecimal transferAmount = BigDecimal.valueOf(1500);
        transactionRequest.setAmount(transferAmount);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountEntity));
        when(accountRepository.findById(accountIdTo)).thenReturn(Optional.of(accountTo));

        assertThrows(TransactionException.class, () -> accountService.transfer(accountIdTo, transactionRequest));

        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, times(1)).findById(accountIdTo);
        verify(accountRepository, never()).updateAccountBalance(any(), any());
        verify(transactionRepository, never()).saveAll(anyList());
    }

    @Test
    void deleteAccount_ShouldDeleteAccount_WhenAccountExists() {
        UUID accountId = DataUtils.getAccountId();
        AccountEntity accountEntity = DataUtils.getAccountEntity();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountEntity));

        accountService.closeAccount(accountId);

        verify(accountRepository).deleteById(accountId);
        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, times(1)).deleteById(accountId);
    }

    @Test
    void deleteAccount_ShouldThrowNotFoundException_WhenAccountDoesNotExist() {
        UUID accountId = DataUtils.getAccountId();
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.closeAccount(accountId));
        verify(accountRepository, times(1)).findById(accountId);
    }
}
