package org.project.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    @NotNull(message = "${transactionRequest.accountId.notNull}")
    @Schema(description = "Идентификатор счета", example = "e7a4f22d-9a5c-4e9d-bc0b-2e0b2a6891cf")
    private UUID accountId;

    @NotNull(message = "${transactionRequest.amount.notNull}")
    @DecimalMin(value = "0.0", inclusive = false, message = "${transactionRequest.amount.decimalMin}")
    @Schema(description = "Сумма транзакции", example = "100.00")
    private BigDecimal amount;
}