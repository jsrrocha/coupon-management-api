package com.challenge.coupon.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name = "coupons")
public class CouponEntity {
    @Id
    private UUID id;

    @Column(nullable = false, length = 6)
    private String code;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal discountValue;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponStatus status;

    @Column(nullable = false)
    private boolean published = false;

    @Column(nullable = false)
    private boolean redeemed = false;
}