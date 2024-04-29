package org.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.dto.response.AccountListResponse;
import org.project.dto.request.AccountRequest;
import org.project.dto.response.AccountResponse;
import org.project.dto.request.TransactionRequest;
import org.project.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
@Tag(name = "Счета", description = "Управление счетами")
public class AccountController {
    private final AccountService accountService;

    @GetMapping(path = "/user/{userId}")
    @Operation(summary = "Получить счета пользователя", description = "Получить список счетов для конкретного пользователя.")
    public ResponseEntity<AccountListResponse> getUserAccounts(
            @PathVariable @Parameter(description = "Идентификатор пользователя") UUID userId) {
        return new ResponseEntity<>(accountService.getUserAccounts(userId), HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    @Operation(summary = "Получить счет по идентификатору", description = "Получить счет по его идентификатору.")
    public ResponseEntity<AccountResponse> getAccountById(
            @PathVariable @Parameter(description = "Идентификатор счета") UUID accountId) {
        return new ResponseEntity<>(accountService.getAccountById(accountId), HttpStatus.OK);
    }

    @PostMapping()
    @Operation(summary = "Создать счет", description = "Создать новый счет.")
    public ResponseEntity<AccountResponse> createAccount(
            @Valid @RequestBody @Parameter(description = "Запрос на создание счета") AccountRequest accountRequest) {
        return new ResponseEntity<>(accountService.createAccount(accountRequest), HttpStatus.CREATED);
    }

    @PutMapping("/deposit")
    @Operation(summary = "Пополнить счет", description = "Пополнить счет.")
    public ResponseEntity<AccountResponse> deposit(
            @Valid @RequestBody @Parameter(description = "Данные о транзакции") TransactionRequest transactionRequest) {
        return new ResponseEntity<>(accountService.deposit(transactionRequest), HttpStatus.OK);
    }

    @PutMapping("/withdraw")
    @Operation(summary = "Снять деньги со счета", description = "Снять деньги со счета.")
    public ResponseEntity<AccountResponse> withdraw(
            @Valid @RequestBody @Parameter(description = "Данные о транзакции") TransactionRequest transactionRequest) {
        return new ResponseEntity<>(accountService.withdraw(transactionRequest), HttpStatus.OK);
    }

    @PutMapping("/transfer/{accountIdTo}")
    @Operation(summary = "Перевести средства на другой счет", description = "Перевести средства на другой счет.")
    public ResponseEntity<AccountResponse> transfer(
            @PathVariable @Parameter(description = "Идентификатор счета-получателя") UUID accountIdTo,
            @Valid @RequestBody @Parameter(description = "Данные о транзакции") TransactionRequest transactionRequest) {
        return new ResponseEntity<>(accountService.transfer(accountIdTo, transactionRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{accountId}")
    @Operation(summary = "Закрыть счет", description = "Закрыть счет.")
    public ResponseEntity<Void> closeAccount(
            @PathVariable @Parameter(description = "Идентификатор счета") UUID accountId) {
        boolean success = accountService.closeAccount(accountId);
        if (success) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
