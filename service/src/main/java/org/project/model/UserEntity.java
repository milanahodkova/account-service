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
@Table(name = "users", schema = "app")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "name")
    private String name;
    @Enumerated(EnumType.STRING)
    private DocumentType docType;
    @Column(name = "doc_num")
    private String docNumber;
}
