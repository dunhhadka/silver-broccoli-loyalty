package org.example.loyalty.loyalty.domain.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int tenantId;

    private Integer customerSapoId;

    private String phone;

    private String fullName;

    private BigDecimal point;

    private BigDecimal pointPeriod;

    private BigDecimal totalSpent;

    private BigDecimal totalSpentPeriod;

    private BigDecimal waitingPlusPoint;

    private BigDecimal usedPointPeriod;

    private BigDecimal totalUsedPoint;

    private String resetPointTriggerName;

    private String resetTierTriggerName;


}
