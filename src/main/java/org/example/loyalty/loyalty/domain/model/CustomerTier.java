package org.example.loyalty.loyalty.domain.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Entity
@Table(name = "customer_tiers",
        uniqueConstraints = {
                @UniqueConstraint(name = "customer_tiers_active_unique", columnNames = {"tenantId", "customerId", "active"})
        })
public class CustomerTier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int tenantId;

    private int customerId;
    private int tierId;

    private Boolean active;

    private int initialTierId;

    private Integer minTierId;
    private BigDecimal qualifiedPoint;
    private BigDecimal initialQualifiedPoint;
    private Instant createdAt;
    private Instant validForm;
    private Instant expireAt;
    private Instant modifiedAt;

    @Enumerated(EnumType.STRING)
    private CustomerTierStatus status;

    public enum CustomerTierStatus {
        ACTIVE, EXPIRED
    }
}
