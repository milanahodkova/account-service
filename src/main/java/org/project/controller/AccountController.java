package org.project.controller;

import lombok.RequiredArgsConstructor;
import org.project.dto.AccountResponse;
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

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<AccountResponse> getAccount (@PathVariable UUID uuid) {
        return new ResponseEntity<>(accountService.getInfo(uuid), HttpStatus.OK);
    }


}
