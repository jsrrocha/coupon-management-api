package com.challenge.coupon.dto;

import com.challenge.coupon.entity.CouponStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder(toBuilder = true)
public record CouponDTO(
        @NotBlank(message = "O código é obrigatório")
        String code,

        @NotBlank(message = "A descrição é obrigatória")
        String description,

        @NotNull(message = "O valor do desconto é obrigatório")
        BigDecimal discountValue,

        @NotNull(message = "A data de expiração é obrigatória")
        LocalDate expirationDate,

        CouponStatus status,

        boolean published
) {}