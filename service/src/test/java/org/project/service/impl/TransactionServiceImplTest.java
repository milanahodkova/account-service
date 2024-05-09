package org.project.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.project.dto.request.TransactionRequest;
import org.project.dto.response.TransactionListResponse;
import org.project.dto.response.TransactionResponse;
import org.project.exception.NotFoundException;
import org.project.model.TransactionEntity;
import org.project.repository.TransactionRepository;
import org.project.util.DataUtils;

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

    @InjectMocks
    private TransactionServiceImpl transactionService;


    @Test
    void getTransactionById_ShouldReturnTransaction_WhenTransactionExists() {
        UUID transactionId = DataUtils.getTransactionId();
        TransactionEntity transactionEntity = DataUtils.getTransactionEntity();
        TransactionResponse transactionResponse = new TransactionResponse();
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transactionEntity));
        when(modelMapper.map(transactionEntity, TransactionResponse.class)).thenReturn(transactionResponse);

        TransactionResponse result = transactionService.getTransactionById(transactionId);

        assertNotNull(result);
        assertEquals(transactionResponse, result);

        verify(transactionRepository, times(1)).findById(transactionId);
    }

    @Test
    void getTransactionById_ShouldThrowNotFoundException_WhenTransactionDoesNotExist() {
        UUID transactionId = DataUtils.getTransactionId();
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> transactionService.getTransactionById(transactionId));

        verify(transactionRepository, times(1)).findById(transactionId);
    }

    @Test
    void getAccountTransactions_ShouldReturnTransactions_WhenTransactionsExist() {
        TransactionEntity transactionEntity = DataUtils.getTransactionEntity();
        UUID accountId = DataUtils.getAccountId();
        TransactionResponse transactionResponse = DataUtils.getTransactionResponse();
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
        TransactionEntity transactionEntity = DataUtils.getTransactionEntity();
        TransactionResponse transactionResponse = DataUtils.getTransactionResponse();
        TransactionRequest transactionRequest = DataUtils.getTransactionRequest();
        when(transactionRepository.save(any(TransactionEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(modelMapper.map(any(TransactionRequest.class), eq(TransactionEntity.class))).thenReturn(transactionEntity);
        when(modelMapper.map(any(TransactionEntity.class), eq(TransactionResponse.class))).thenReturn(transactionResponse);

        TransactionResponse result = transactionService.createTransaction(transactionRequest);

        assertNotNull(result);
        assertEquals(transactionResponse, result);

        verify(transactionRepository, times(1)).save(any(TransactionEntity.class));
    }
}
