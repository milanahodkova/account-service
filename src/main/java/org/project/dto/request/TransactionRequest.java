package org.project.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    @NotNull(message = "${transactionRequest.accountId.notNull}")
    private UUID accountId;

    @NotNull(message = "${transactionRequest.amount.notNull}")
    @DecimalMin(value = "0.0", inclusive = false, message = "${transactionRequest.amount.decimalMin}")
    private BigDecimal amount;
}