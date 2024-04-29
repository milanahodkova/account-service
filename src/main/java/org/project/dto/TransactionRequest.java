package org.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.project.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private UUID accountId;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private TransactionType transactionType;
}
