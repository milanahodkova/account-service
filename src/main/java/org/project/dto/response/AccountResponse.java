package org.project.dto.response;

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
    private UUID id;
    private Currency currency;
    private BigDecimal balance;
    private UserResponse user;
}
