package org.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.dto.response.TransactionListResponse;
import org.project.dto.request.TransactionRequest;
import org.project.dto.response.TransactionResponse;
import org.project.exception.NotFoundException;
import org.project.model.TransactionEntity;
import org.project.repository.TransactionRepository;
import org.project.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public TransactionResponse getTransactionById(UUID transactionId) {
        TransactionEntity transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));
        return convertToDto(transaction);
    }

    @Override
    public TransactionListResponse getAccountTransactions(UUID accountId) {
        List<TransactionEntity> transactions = transactionRepository.findByAccount_Id(accountId);
        return new TransactionListResponse(
                transactions.stream()
                        .map(this::convertToDto)
                        .toList()
        );
    }

    @Override
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        TransactionEntity transaction = convertToEntity(transactionRequest);
        return convertToDto(transactionRepository.save(transaction));
    }

    private TransactionResponse convertToDto(TransactionEntity transaction) {
        return modelMapper.map(transaction, TransactionResponse.class);
    }

    private TransactionEntity convertToEntity(TransactionRequest transactionRequest) {
        return modelMapper.map(transactionRequest, TransactionEntity.class);
    }

}
