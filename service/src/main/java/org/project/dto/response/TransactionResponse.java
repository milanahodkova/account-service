package org.project.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.project.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    @Schema(description = "Идентификатор транзакции")
    private UUID id;

    @Schema(description = "Информация о счете")
    private AccountResponse account;

    @Schema(description = "Сумма транзакции")
    private BigDecimal amount;

    @Schema(description = "Временная метка транзакции")
    private LocalDateTime timestamp;

    @Schema(description = "Тип транзакции")
    private TransactionType transactionType;
}
