package org.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.project.dto.response.TransactionListResponse;
import org.project.dto.response.TransactionResponse;
import org.project.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
@Tag(name = "Транзакции", description = "Управление транзакциями")
public class TransactionController {
    private final TransactionService transactionService;

    @Operation(
            summary = "Получить транзакцию по идентификатору",
            description = "Получить информацию о транзакции по ее идентификатору."
    )
    @GetMapping("/{transactionId}")
    public TransactionResponse getTransactionById(
            @PathVariable("transactionId") @Parameter(description = "Идентификатор транзакции") UUID transactionId) {
        return transactionService.getTransactionById(transactionId);
    }

    @Operation(
            summary = "Получить транзакции по идентификатору счета",
            description = "Получить список транзакций для конкретного счета."
    )
    @GetMapping("/account/{accountId}")
    public TransactionListResponse getAccountTransactions(
            @PathVariable("accountId") @Parameter(description = "Идентификатор счета") UUID accountId) {
        return transactionService.getAccountTransactions(accountId);
    }
}