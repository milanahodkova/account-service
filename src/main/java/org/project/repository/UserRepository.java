package org.project.repository;

import org.project.model.DocumentType;
import org.project.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    boolean existsByDocTypeAndDocNumber(DocumentType docType, String docNumber);
}

