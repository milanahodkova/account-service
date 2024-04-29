package org.project.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionListResponse {
    @JsonProperty("transactionResponseList")
    @Schema(description = "Список ответов транзакций")
    private List<TransactionResponse> transactionResponseList;
}
