package org.project.util;

import org.project.dto.request.AccountRequest;
import org.project.dto.request.TransactionRequest;
import org.project.dto.request.UserRequest;
import org.project.dto.response.AccountResponse;
import org.project.dto.response.TransactionResponse;
import org.project.dto.response.UserResponse;
import org.project.model.AccountEntity;
import org.project.model.TransactionEntity;
import org.project.model.UserEntity;
import org.project.model.enums.Currency;
import org.project.model.enums.DocumentType;
import org.project.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class DataUtils {
    private static final UUID userId = UUID.randomUUID();
    private static final UserEntity userEntity = getUserEntity();
    private static final UserResponse userResponse = getUserResponse();
    private static final UUID accountId = UUID.randomUUID();
    private static final AccountEntity accountEntity = getAccountEntity();
    private static final AccountResponse accountResponse = getAccountResponse();
    private static final LocalDateTime timestamp = LocalDateTime.now();
    private static final UUID transactionId = UUID.randomUUID();

    public static UserEntity getUserEntity(){
        return UserEntity.builder()
                .id(userId)
                .name("John Smith")
                .docType(DocumentType.PASSPORT)
                .docNumber("ABCD1234")
                .build();
    }

    public static UserRequest getUserRequest(){
        return UserRequest.builder()
                .name("John Smith")
                .docType(DocumentType.PASSPORT)
                .docNumber("ABCD1234")
                .build();
    }

    public static UserResponse getUserResponse(){
        return UserResponse.builder()
                .id(userId)
                .name("John Smith")
                .docType(DocumentType.PASSPORT)
                .docNumber("ABCD1234")
                .build();
    }

    public static AccountEntity getAccountEntity(){
        return AccountEntity.builder()
                .id(accountId)
                .user(userEntity)
                .balance(BigDecimal.valueOf(1000))
                .currency(Currency.USD)
                .build();
    }

    public static AccountRequest getAccountRequest(){
        return AccountRequest.builder()
                .balance(BigDecimal.valueOf(1000))
                .currency(Currency.USD)
                .userId(userEntity.getId())
                .build();
    }

    public static AccountResponse getAccountResponse(){
        return AccountResponse.builder()
                .id(accountId)
                .user(userResponse)
                .balance(BigDecimal.valueOf(1000))
                .currency(Currency.USD)
                .build();
    }


    public static TransactionEntity getTransactionEntity(){
        return TransactionEntity.builder()
                .id(transactionId)
                .account(accountEntity)
                .amount(BigDecimal.valueOf(100.0))
                .timestamp(timestamp)
                .transactionType(TransactionType.DEPOSIT)
                .build();
    }

    public static TransactionRequest getTransactionRequest(){
        return TransactionRequest.builder()
                .accountId(accountId)
                .amount(BigDecimal.valueOf(100.0))
                .build();
    }

    public static TransactionResponse getTransactionResponse(){
        return TransactionResponse.builder()
                .id(transactionId)
                .account(accountResponse)
                .amount(BigDecimal.valueOf(100.0))
                .timestamp(timestamp)
                .transactionType(TransactionType.DEPOSIT)
                .build();
    }

    public static UUID getUserId() {
        return userId;
    }

    public static UUID getAccountId() {
        return accountId;
    }

    public static UUID getTransactionId() {
        return accountId;
    }
}
