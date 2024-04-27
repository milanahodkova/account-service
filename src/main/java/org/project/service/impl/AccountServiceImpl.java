package org.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.dto.AccountResponse;
import org.project.exception.EntityNotFoundException;
import org.project.model.AccountEntity;
import org.project.model.UserEntity;
import org.project.repository.AccountRepository;
import org.project.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Override
    public AccountResponse getInfo(UUID uuid) {
        AccountEntity account = accountRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
        return convertToDto(account);
    }

    private AccountResponse convertToDto(AccountEntity user) {
        return modelMapper.map(user, AccountResponse.class);
    }
}
