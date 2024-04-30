package org.project.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.project.model.enums.Currency;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    @NotBlank(message = "${accountRequest.currency.notBlank}")
    @Schema(description = "Валюта счета")
    private Currency currency;

    @NotNull(message = "${accountRequest.balance.notNull}")
    @DecimalMin(value = "0.00", message = "${accountRequest.balance.decimalMin}")
    @Schema(description = "Баланс счета", example = "1000.00")
    private BigDecimal balance;

    @NotNull(message = "${accountRequest.userId.notNull}")
    @Schema(description = "Идентификатор пользователя", example = "e7a4f22d-9a5c-4e9d-bc0b-2e0b2a6891cf")
    private UUID userId;
}
