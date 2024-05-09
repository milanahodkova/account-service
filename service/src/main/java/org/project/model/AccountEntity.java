package org.project.model;

import jakarta.persistence.*;
import lombok.*;
import org.project.model.enums.Currency;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;
    @Column(precision = 10, scale = 2)
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private Currency currency;

}
