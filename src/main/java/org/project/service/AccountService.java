package org.project.service;

import org.project.dto.AccountResponse;

import java.util.UUID;

public interface AccountService {
    AccountResponse getInfo(UUID uuid);
}
