package org.project.util;

import org.project.dto.request.UserRequest;
import org.project.dto.response.UserResponse;
import org.project.model.UserEntity;
import org.project.model.enums.DocumentType;

import java.util.UUID;

public class DataUtils {
    private static final UUID userId = UUID.randomUUID();

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

    public static UUID getUserId() {
        return userId;
    }
}
