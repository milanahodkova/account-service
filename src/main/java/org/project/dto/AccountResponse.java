package org.project.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.project.model.Currency;
import org.project.model.UserEntity;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountResponse {
    private UUID uuid;
    private Currency currency;
    private BigDecimal balance;
    private UserResponse user;
}
