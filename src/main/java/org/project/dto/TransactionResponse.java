package org.project.dto;

import lombok.*;
import org.project.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionResponse {
    private UUID id;
    private AccountResponse account;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private TransactionType transactionType;
}
