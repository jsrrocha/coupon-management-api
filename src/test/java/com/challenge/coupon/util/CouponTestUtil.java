package com.challenge.coupon.util;

import com.challenge.coupon.domain.Coupon;
import com.challenge.coupon.dto.CouponDTO;
import com.challenge.coupon.dto.CouponResponseDTO;
import com.challenge.coupon.entity.CouponEntity;
import com.challenge.coupon.entity.CouponStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class CouponTestUtil {

    public static CouponDTO createValidCouponDTO() {
        return CouponDTO.builder()
                .code("SAVE10")
                .description("10% de desconto")
                .discountValue(new BigDecimal("10.0"))
                .expirationDate(LocalDate.now().plusDays(10))
                .status(CouponStatus.ACTIVE)
                .published(true)
                .build();
    }


    public static Coupon createValidCoupon() {
        return toDomain(createValidCouponDTO());
    }

    public static Coupon toDomain(CouponDTO dto) {
        return Coupon.create(
                dto.code(),
                dto.description(),
                dto.discountValue(),
                dto.expirationDate(),
                dto.status(),
                dto.published()
        );
    }

    public static CouponResponseDTO createValidResponseDTO(UUID id) {
        return new CouponResponseDTO(
                id,
                "SAVE10",
                "Descrição da Resposta",
                new BigDecimal("10.0"),
                LocalDate.now().plusDays(10),
                CouponStatus.ACTIVE,
                true,
                false
        );
    }

    public static CouponEntity createValidCouponEntity() {
        CouponEntity entity = new CouponEntity();
        entity.setId(UUID.randomUUID());
        entity.setCode("SAVE10");
        entity.setDescription("Descrição da Entidade");
        entity.setDiscountValue(new BigDecimal("10.0"));
        entity.setExpirationDate(LocalDate.now().plusDays(10));
        entity.setStatus(CouponStatus.ACTIVE);
        entity.setPublished(true);
        entity.setRedeemed(false);
        return entity;
    }

}