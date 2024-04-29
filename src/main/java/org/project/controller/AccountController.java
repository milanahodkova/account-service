package org.project.controller;

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
public class AccountController {
    private final AccountService accountService;

    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<AccountListResponse> getUserAccounts(@PathVariable UUID userId) {
        return new ResponseEntity<>(accountService.getUserAccounts(userId), HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable UUID accountId) {
        return new ResponseEntity<>(accountService.getAccountById(accountId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountRequest accountRequest) {
        return new ResponseEntity<>(accountService.createAccount(accountRequest), HttpStatus.CREATED);
    }

    @PutMapping("/deposit")
    public ResponseEntity<AccountResponse> deposit(@Valid @RequestBody TransactionRequest transactionRequest) {
        return new ResponseEntity<>(accountService.deposit(transactionRequest), HttpStatus.OK);
    }

    @PutMapping("/withdraw")
    public ResponseEntity<AccountResponse> withdraw(@Valid @RequestBody TransactionRequest transactionRequest) {
        return new ResponseEntity<>(accountService.withdraw(transactionRequest), HttpStatus.OK);
    }

    @PutMapping("/transfer/{accountIdTo}")
    public ResponseEntity<AccountResponse> transfer(@PathVariable UUID accountIdTo,
                                                    @Valid @RequestBody TransactionRequest transactionRequest) {
        return new ResponseEntity<>(accountService.transfer(accountIdTo, transactionRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> closeAccount(@PathVariable UUID accountId) {
        boolean success = accountService.closeAccount(accountId);
        if (success) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
