package org.project.dto;

import jakarta.persistence.Table;
import lombok.*;
import org.project.model.Document;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {
    private UUID uuid;
    private String name;
    private Document document;
}

