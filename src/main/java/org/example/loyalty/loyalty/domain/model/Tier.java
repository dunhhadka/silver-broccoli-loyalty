package org.example.loyalty.loyalty.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.loyalty.loyalty.application.converter.StringListConverter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "tiers")
public class Tier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int tenantId;

    private String name;

    private BigDecimal minCondition;

    private BigDecimal discount;

    private BigDecimal maxDiscountAmount;

    private BigDecimal conditionEndow;

    private boolean status = true;

    private Boolean flag = true;

    private Instant createdAt;

    private Integer members;

    @OneToMany(mappedBy = "tier", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reward> rewards = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    private List<String> descriptions;
}
