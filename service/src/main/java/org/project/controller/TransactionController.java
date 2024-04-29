package org.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.dto.response.TransactionListResponse;
import org.project.dto.request.TransactionRequest;
import org.project.dto.response.TransactionResponse;
import org.project.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
@Tag(name = "Транзакции", description = "Управление транзакциями")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/{transactionId}")
    @Operation(
            summary = "Получить транзакцию по идентификатору",
            description = "Получить информацию о транзакции по ее идентификатору."
    )
    public ResponseEntity<TransactionResponse> getTransactionById(
            @PathVariable("transactionId") @Parameter(description = "Идентификатор транзакции") UUID transactionId) {
        TransactionResponse transactionResponse = transactionService.getTransactionById(transactionId);
        if (transactionResponse != null) {
            return ResponseEntity.ok(transactionResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/account/{accountId}")
    @Operation(
            summary = "Получить транзакции по идентификатору счета",
            description = "Получить список транзакций для конкретного счета."
    )
    public ResponseEntity<TransactionListResponse> getAccountTransactions(
            @PathVariable("accountId") @Parameter(description = "Идентификатор счета") UUID accountId) {
        return new ResponseEntity<>(transactionService.getAccountTransactions(accountId), HttpStatus.OK);
    }

    @PostMapping
    @Operation(
            summary = "Создать транзакцию",
            description = "Создать новую транзакцию."
    )
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody @Parameter(description = "Запрос на создание транзакции") TransactionRequest transactionRequest) {
        return new ResponseEntity<>(transactionService.createTransaction(transactionRequest), HttpStatus.CREATED);
    }
}