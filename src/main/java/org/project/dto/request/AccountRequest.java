package org.project.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.project.model.enums.Currency;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    @NotBlank(message = "${accountRequest.currency.notBlank}")
    private Currency currency;

    @NotNull(message = "${accountRequest.balance.notNull}")
    @DecimalMin(value = "0.00", message = "${accountRequest.balance.decimalMin}")
    private BigDecimal balance;

    @NotNull(message = "${accountRequest.userId.notNull}")
    private UUID userId;
}
