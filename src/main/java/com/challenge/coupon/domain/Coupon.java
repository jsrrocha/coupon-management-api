package com.challenge.coupon.domain;

import com.challenge.coupon.entity.CouponStatus;
import com.challenge.coupon.exception.*;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class Coupon {
    private final UUID id;
    private final String code;
    private final String description;
    private final BigDecimal discountValue;
    private final LocalDate expirationDate;
    private final CouponStatus status;
    private final boolean published;
    private final boolean redeemed;

    public Coupon(UUID id, String code, String description, BigDecimal discountValue,
                   LocalDate expirationDate, CouponStatus status, boolean published, boolean redeemed) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.discountValue = discountValue;
        this.expirationDate = expirationDate;
        this.status = status;
        this.published = published;
        this.redeemed = redeemed;
    }

    public static Coupon create(String code, String description, BigDecimal discountValue,
                                   LocalDate expirationDate, CouponStatus status, boolean published) {
        return new Coupon(
                UUID.randomUUID(),
                ensureSixCharacters(code),
                description,
                ensureMinimumDiscount(discountValue),
                ensureFutureDate(expirationDate),
                status == null ? CouponStatus.ACTIVE : status,
                published,
                false
        );
    }

    public Coupon delete() {
        if (this.status == CouponStatus.DELETED) {
            throw new CouponAlreadyDeletedException(this.id);
        }
        return this.toBuilder().status(CouponStatus.DELETED).build();
    }

    public Coupon markAsRedeemed() {
        if (this.status != CouponStatus.ACTIVE) {
            throw new CouponNotActiveException("Não é possível resgatar um cupom que não esteja ativo");
        }
        if (this.expirationDate.isBefore(LocalDate.now())) {
            throw new CouponExpiredException("O cupom expirou em " + this.expirationDate);
        }
        if (this.redeemed) {
            throw new CouponAlreadyRedeemedException("Este cupom já foi resgatado.");
        }
        return this.toBuilder().redeemed(true).build();
    }

    private static String ensureSixCharacters(String rawCode) {
        String cleaned = rawCode.replaceAll("[^a-zA-Z0-9]", "");
        if (cleaned.length() != 6) {
            throw new InvalidCouponCodeException("O código deve ter exatamente 6 caracteres alfanuméricos.");        }
        return cleaned.toUpperCase();
    }

    private static BigDecimal ensureMinimumDiscount(BigDecimal value) {
        if (value.compareTo(new BigDecimal("0.5")) < 0)
            throw new CouponDiscountBelowMinimumException("O desconto mínimo permitido é 0.5.");        return value;
    }

    private static LocalDate ensureFutureDate(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new InvalidExpirationDateException("A data de expiração não pode ser anterior à data atual.");        }
        return date;
    }



}