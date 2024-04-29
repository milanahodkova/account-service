package org.project.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.project.model.enums.Currency;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    @Schema(description = "Идентификатор счета")
    private UUID id;

    @Schema(description = "Валюта счета")
    private Currency currency;

    @Schema(description = "Баланс счета")
    private BigDecimal balance;

    @Schema(description = "Информация о пользователе")
    private UserResponse user;
}
