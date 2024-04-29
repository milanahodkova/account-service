package org.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.dto.TransactionListResponse;
import org.project.dto.TransactionRequest;
import org.project.dto.TransactionResponse;
import org.project.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable("transactionId") UUID transactionId) {
        TransactionResponse transactionResponse = transactionService.getTransactionById(transactionId);
        if (transactionResponse != null) {
            return ResponseEntity.ok(transactionResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<TransactionListResponse> getAccountTransactions(@PathVariable("accountId") UUID accountId) {
        return new ResponseEntity<>(transactionService.getAccountTransactions(accountId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {
        return new ResponseEntity<>(transactionService.createTransaction(transactionRequest), HttpStatus.CREATED);
    }

}
