package org.project.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.project.dto.request.TransactionRequest;
import org.project.dto.response.TransactionListResponse;
import org.project.dto.response.TransactionResponse;
import org.project.exception.NotFoundException;
import org.project.model.TransactionEntity;
import org.project.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private ModelMapper modelMapper;

    private TransactionServiceImpl transactionService;

    private UUID transactionId;
    private UUID accountId;
    private TransactionRequest transactionRequest;
    private TransactionResponse transactionResponse;
    private TransactionEntity transactionEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionService = new TransactionServiceImpl(transactionRepository, modelMapper);

        this.transactionId = UUID.randomUUID();
        this.accountId = UUID.randomUUID();

        this.transactionRequest = new TransactionRequest();
        transactionRequest.setAccountId(accountId);
        transactionRequest.setAmount(BigDecimal.valueOf(1000));

        this.transactionResponse = new TransactionResponse();
        transactionResponse.setId(transactionId);
        transactionResponse.setAmount(BigDecimal.valueOf(1000));

        this.transactionEntity = new TransactionEntity();
        transactionEntity.setId(transactionId);
        transactionEntity.setAmount(BigDecimal.valueOf(1000));
    }

    @Test
    void getTransactionById_ShouldReturnTransaction_WhenTransactionExists() {
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transactionEntity));
        when(modelMapper.map(transactionEntity, TransactionResponse.class)).thenReturn(transactionResponse);

        TransactionResponse result = transactionService.getTransactionById(transactionId);

        assertNotNull(result);
        assertEquals(transactionResponse, result);

        verify(transactionRepository, times(1)).findById(transactionId);
    }

    @Test
    void getTransactionById_ShouldThrowNotFoundException_WhenTransactionDoesNotExist() {
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> transactionService.getTransactionById(transactionId));

        verify(transactionRepository, times(1)).findById(transactionId);
    }

    @Test
    void getAccountTransactions_ShouldReturnTransactions_WhenTransactionsExist() {
        List<TransactionEntity> transactionEntities = new ArrayList<>();
        transactionEntities.add(transactionEntity);

        when(transactionRepository.findByAccount_Id(accountId)).thenReturn(transactionEntities);
        when(modelMapper.map(transactionEntity, TransactionResponse.class)).thenReturn(transactionResponse);

        TransactionListResponse result = transactionService.getAccountTransactions(accountId);

        assertNotNull(result);
        assertEquals(1, result.getTransactionResponseList().size());
        assertEquals(transactionResponse, result.getTransactionResponseList().getFirst());

        verify(transactionRepository, times(1)).findByAccount_Id(accountId);
    }

    @Test
    void createTransaction_ShouldCreateTransaction() {
        when(transactionRepository.save(any(TransactionEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(modelMapper.map(any(TransactionRequest.class), eq(TransactionEntity.class))).thenReturn(transactionEntity);
        when(modelMapper.map(any(TransactionEntity.class), eq(TransactionResponse.class))).thenReturn(transactionResponse);

        TransactionResponse result = transactionService.createTransaction(transactionRequest);

        assertNotNull(result);
        assertEquals(transactionResponse, result);

        verify(transactionRepository, times(1)).save(any(TransactionEntity.class));
    }
}
