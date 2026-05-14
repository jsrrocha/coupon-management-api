package com.challenge.coupon.unit.domain;

import com.challenge.coupon.domain.Coupon;
import com.challenge.coupon.entity.CouponStatus;
import com.challenge.coupon.exception.CouponAlreadyDeletedException;
import com.challenge.coupon.exception.CouponDiscountBelowMinimumException;
import com.challenge.coupon.exception.InvalidCouponCodeException;
import com.challenge.coupon.exception.InvalidExpirationDateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.challenge.coupon.util.CouponTestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Testes de Domínio: Coupon")
class CouponTest {

    @Test
    @DisplayName("Deve criar um cupom válido com todos os dados corretos")
    void shouldCreateValidCoupon() {
        Coupon coupon = createValidCoupon();

        assertThat(coupon.getId()).isNotNull();
        assertThat(coupon.getCode()).isEqualTo("SAVE10");
        assertThat(coupon.getDiscountValue()).isEqualByComparingTo("10.0");
        assertThat(coupon.getStatus()).isEqualTo(CouponStatus.ACTIVE);
        assertThat(coupon.isRedeemed()).isFalse();
    }

    @Test
    @DisplayName("Deve definir status como ACTIVE quando for nulo na criação")
    void shouldDefaultToActiveWhenStatusIsNull() {
        var dto = createValidCouponDTO().toBuilder()
                .status(null)
                .build();

        assertThat(toDomain(dto).getStatus()).isEqualTo(CouponStatus.ACTIVE);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o código não tiver 6 caracteres")
    void shouldThrowExceptionWhenCodeIsInvalid() {
        var dto = createValidCouponDTO().toBuilder()
                .code("ABC11112")
                .build();

        assertThatThrownBy(() -> toDomain(dto))
                .isInstanceOf(InvalidCouponCodeException.class);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o desconto for menor que 0.5")
    void shouldThrowExceptionWhenDiscountIsTooLow() {
        var dto = createValidCouponDTO().toBuilder()
                .discountValue(new BigDecimal("0.4"))
                .build();

        assertThatThrownBy(() -> toDomain(dto))
                .isInstanceOf(CouponDiscountBelowMinimumException.class);
    }

    @Test
    @DisplayName("Deve lançar exceção quando a data de expiração for no passado")
    void shouldThrowExceptionWhenDateIsPast() {
        var dto = createValidCouponDTO().toBuilder()
                .expirationDate(LocalDate.now().minusDays(1))
                .build();

        assertThatThrownBy(() -> toDomain(dto))
                .isInstanceOf(InvalidExpirationDateException.class);
    }


    @Test
    @DisplayName("Deve permitir exclusão se o cupom não estiver deletado")
    void shouldAllowDeletionWhenStatusIsActive() {
        Coupon coupon = createValidCoupon();
        coupon.validateCanBeDeleted();
    }

    @Test
    @DisplayName("Deve lançar exceção ao validar exclusão de um cupom já deletado")
    void shouldThrowExceptionWhenAlreadyDeleted() {
        var dto = createValidCouponDTO().toBuilder()
                .status(CouponStatus.DELETED)
                .build();

        assertThatThrownBy(toDomain(dto)::validateCanBeDeleted)
                .isInstanceOf(CouponAlreadyDeletedException.class);
    }

}