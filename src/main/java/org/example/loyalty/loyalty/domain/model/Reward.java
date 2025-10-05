package org.example.loyalty.loyalty.domain.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "rewards")
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int tenantId;

    private String name;

    @Enumerated(EnumType.STRING)
    private RewardType type;

    private BigDecimal point;

    @ManyToOne
    @JoinColumn(name = "tier_id")
    private Tier tier;

    public enum RewardType {
        POINT_REWARD
    }
}
