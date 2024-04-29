package org.project.dto;

import lombok.*;
import org.project.model.DocumentType;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {
    private UUID id;
    private String name;
    private DocumentType docType;
    private String docNumber;
}

