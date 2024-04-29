package org.project.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.project.model.enums.DocumentType;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {
    @Schema(description = "Идентификатор пользователя")
    private UUID id;

    @Schema(description = "Имя пользователя")
    private String name;

    @Schema(description = "Тип документа")
    private DocumentType docType;

    @Schema(description = "Номер документа")
    private String docNumber;
}

