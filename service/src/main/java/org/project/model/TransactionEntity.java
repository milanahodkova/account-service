package org.project.model;


import jakarta.persistence.*;
import lombok.*;
import org.project.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions", schema = "app")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private AccountEntity account;
    @Column(precision = 10, scale = 2)
    private BigDecimal amount;
    private LocalDateTime timestamp;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
}
