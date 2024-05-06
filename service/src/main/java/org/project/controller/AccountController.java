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

    @Operation(summary = "Получить счета пользователя", description = "Получить список счетов для конкретного пользователя.")
    @GetMapping(path = "/user/{userId}")
    public AccountListResponse getUserAccounts(
            @PathVariable @Parameter(description = "Идентификатор пользователя") UUID userId) {
        return accountService.getUserAccounts(userId);
    }

    @Operation(summary = "Получить счет по идентификатору", description = "Получить счет по его идентификатору.")
    @GetMapping("/{accountId}")
    public AccountResponse getAccountById(
            @PathVariable @Parameter(description = "Идентификатор счета") UUID accountId) {
        return accountService.getAccountById(accountId);
    }

    @Operation(summary = "Создать счет", description = "Создать новый счет.")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(
            @Valid @RequestBody @Parameter(description = "Запрос на создание счета") AccountRequest accountRequest) {
        return accountService.createAccount(accountRequest);
    }

    @Operation(summary = "Пополнить счет", description = "Пополнить счет.")
    @PutMapping("/deposit")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse deposit(
            @Valid @RequestBody @Parameter(description = "Данные о транзакции") TransactionRequest transactionRequest) {
        return accountService.deposit(transactionRequest);
    }

    @Operation(summary = "Снять деньги со счета", description = "Снять деньги со счета.")
    @PutMapping("/withdraw")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse withdraw(
            @Valid @RequestBody @Parameter(description = "Данные о транзакции") TransactionRequest transactionRequest) {
        return accountService.withdraw(transactionRequest);
    }

    @Operation(summary = "Перевести средства на другой счет", description = "Перевести средства на другой счет.")
    @PutMapping("/transfer/{accountIdTo}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse transfer(
            @PathVariable @Parameter(description = "Идентификатор счета-получателя") UUID accountIdTo,
            @Valid @RequestBody @Parameter(description = "Данные о транзакции") TransactionRequest transactionRequest) {
        return accountService.transfer(accountIdTo, transactionRequest);
    }

    @Operation(summary = "Закрыть счет", description = "Закрыть счет.")
    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closeAccount(
            @PathVariable @Parameter(description = "Идентификатор счета") UUID accountId) {
        accountService.closeAccount(accountId);
    }
}
