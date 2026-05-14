package com.challenge.coupon.dto;

import com.challenge.coupon.entity.CouponStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CouponResponseDTO(
        UUID id,
        String code,
        String description,
        BigDecimal discountValue,
        LocalDate expirationDate,
        CouponStatus status,
        boolean published,
        boolean redeemed
) {}