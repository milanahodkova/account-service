package org.project.repository;

import org.project.model.AccountEntity;
import org.project.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    List<AccountEntity> findAllByUser(UserEntity user);

    @Modifying(clearAutomatically=true, flushAutomatically = true)
    @Query("UPDATE AccountEntity a SET a.balance = a.balance - :amount WHERE a.id = :accountId")
    void updateAccountBalance(@Param("accountId") UUID accountId, @Param("amount") BigDecimal amount);
}


