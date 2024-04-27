package org.project.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.project.dto.DocumentType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Document {
    @Enumerated(EnumType.STRING)
    private DocumentType docType;
    private String docNumber;
}
