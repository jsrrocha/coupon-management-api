package com.challenge.coupon.unit.dto;

import com.challenge.coupon.dto.CouponDTO;
import com.challenge.coupon.util.CouponTestUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testes de DTO: CouponDTO")
class CouponDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Deve validar com sucesso quando todos os campos obrigatórios estão presentes")
    void shouldValidateSuccessfully() {
        CouponDTO dto = CouponTestUtil.createValidCouponDTO();

        Set<ConstraintViolation<CouponDTO>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Deve falhar quando o código está em branco")
    void shouldFailWhenCodeIsBlank() {
        CouponDTO dto = CouponTestUtil.createValidCouponDTO().toBuilder()
                .code("")
                .build();

        Set<ConstraintViolation<CouponDTO>> violations = validator.validate(dto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("O código é obrigatório");
    }

    @Test
    @DisplayName("Deve falhar quando a descrição está em branco")
    void shouldFailWhenDescriptionIsBlank() {
        CouponDTO dto = CouponTestUtil.createValidCouponDTO().toBuilder()
                .description(null)
                .build();

        Set<ConstraintViolation<CouponDTO>> violations = validator.validate(dto);

        assertThat(violations).anyMatch(v ->
                v.getMessage().equals("A descrição é obrigatória"));
    }

    @Test
    @DisplayName("Deve falhar quando o valor do desconto é nulo")
    void shouldFailWhenDiscountValueIsNull() {
        CouponDTO dto = CouponTestUtil.createValidCouponDTO().toBuilder()
                .discountValue(null)
                .build();

        Set<ConstraintViolation<CouponDTO>> violations = validator.validate(dto);
        assertThat(violations).anyMatch(v ->
                v.getMessage().equals("O valor do desconto é obrigatório"));
    }

    @Test
    @DisplayName("Deve falhar quando a data de expiração é nula")
    void shouldFailWhenExpirationDateIsNull() {
        CouponDTO dto = CouponTestUtil.createValidCouponDTO().toBuilder()
                .expirationDate(null)
                .build();

        Set<ConstraintViolation<CouponDTO>> violations = validator.validate(dto);
        assertThat(violations).anyMatch(v ->
                v.getMessage().equals("A data de expiração é obrigatória"));
    }

}