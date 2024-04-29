package org.project.model;

import jakarta.persistence.*;
import lombok.*;
import org.project.model.enums.DocumentType;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "name")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "doc_type")
    private DocumentType docType;
    @Column(name = "doc_num")
    private String docNumber;
}
