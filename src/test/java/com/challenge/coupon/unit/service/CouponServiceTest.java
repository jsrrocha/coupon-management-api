package com.challenge.coupon.unit.service;

import com.challenge.coupon.domain.Coupon;
import com.challenge.coupon.dto.CouponDTO;
import com.challenge.coupon.dto.CouponResponseDTO;
import com.challenge.coupon.entity.CouponEntity;
import com.challenge.coupon.entity.CouponStatus;
import com.challenge.coupon.exception.*;
import com.challenge.coupon.mapper.CouponMapper;
import com.challenge.coupon.repository.CouponRepository;
import com.challenge.coupon.service.CouponService;
import com.challenge.coupon.util.CouponTestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static com.challenge.coupon.util.CouponTestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de Unidade: CouponService")
class CouponServiceTest {

    @Mock
    private CouponRepository repository;

    @Mock
    private CouponMapper mapper;

    @InjectMocks
    private CouponService service;

    @Test
    @DisplayName("Deve criar um cupom com sucesso")
    void shouldCreateCouponSuccessfully() {
        CouponDTO dto = createValidCouponDTO();
        CouponEntity entity = createValidCouponEntity();
        CouponResponseDTO expectedResponse = createValidResponseDTO(UUID.randomUUID());

        when(mapper.toEntity(any(Coupon.class))).thenReturn(entity);
        when(repository.save(any(CouponEntity.class))).thenReturn(entity);
        when(mapper.toResponseDTO(entity)).thenReturn(expectedResponse);

        CouponResponseDTO result = service.create(dto);

        assertThat(result).isEqualTo(expectedResponse);
        verify(repository).save(any(CouponEntity.class));
    }

    @Test
    @DisplayName("Deve buscar por ID com sucesso")
    void shouldFindByIdSuccessfully() {
        UUID id = UUID.randomUUID();
        CouponEntity entity = createValidCouponEntity();
        CouponResponseDTO expectedResponse = createValidResponseDTO(id);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toResponseDTO(entity)).thenReturn(expectedResponse);

        CouponResponseDTO result = service.findById(id);

        assertThat(result).isEqualTo(expectedResponse);
    }


    @Test
    @DisplayName("Deve lançar exceção quando cupom não for encontrado")
    void shouldThrowExceptionWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(CouponNotFoundException.class);
    }

    @Test
    @DisplayName("Deve executar soft delete com sucesso")
    void shouldDeleteSuccessfully() {
        UUID id = UUID.randomUUID();
        CouponEntity entity = createValidCouponEntity();
        Coupon domain = createValidCoupon();

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        service.delete(id);

        assertThat(entity.getStatus()).isEqualTo(CouponStatus.DELETED);
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar um cupom que não existe")
    void shouldThrowExceptionWhenDeletingNonExistentCoupon() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(id))
                .isInstanceOf(CouponNotFoundException.class);

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar um cupom que já está com status DELETED")
    void shouldThrowExceptionWhenCouponIsAlreadyDeleted() {
        UUID id = UUID.randomUUID();
        CouponEntity entity = CouponTestUtil.createValidCouponEntity();

        var deletedDto = CouponTestUtil.createValidCouponDTO().toBuilder()
                .status(CouponStatus.DELETED)
                .build();
        Coupon domain = CouponTestUtil.toDomain(deletedDto);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        assertThatThrownBy(() -> service.delete(id))
                .isInstanceOf(CouponAlreadyDeletedException.class);

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve executar o resgate (redeem) com sucesso")
    void shouldRedeemSuccessfully() {
        UUID id = UUID.randomUUID();
        CouponEntity entity = createValidCouponEntity(); // redeemed está false
        Coupon domain = createValidCoupon(); // Ativo e dentro da validade

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        service.redeem(id);

        assertThat(entity.isRedeemed()).isTrue();
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar resgatar um cupom inexistente")
    void shouldThrowExceptionWhenRedeemingNonExistent() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.redeem(id))
                .isInstanceOf(CouponNotFoundException.class);

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar resgatar cupom vencido")
    void shouldThrowExceptionWhenRedeemingExpired() {
        UUID id = UUID.randomUUID();
        CouponEntity entity = createValidCouponEntity();

        Coupon expiredDomain = CouponTestUtil.createExpiredCoupon();

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(expiredDomain);

        assertThatThrownBy(() -> service.redeem(id))
                .isInstanceOf(CouponExpiredException.class);

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar resgatar cupom já resgatado")
    void shouldThrowExceptionWhenAlreadyRedeemed() {
        UUID id = UUID.randomUUID();
        CouponEntity entity = createValidCouponEntity();

        Coupon redeemedDomain = createValidCoupon().toBuilder()
                .redeemed(true)
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(redeemedDomain);

        assertThatThrownBy(() -> service.redeem(id))
                .isInstanceOf(CouponAlreadyRedeemedException.class);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar resgatar cupom inativo")
    void shouldThrowExceptionWhenRedeemingInactive() {
        UUID id = UUID.randomUUID();
        CouponEntity entity = createValidCouponEntity();

        Coupon inactiveDomain = createValidCoupon().toBuilder()
                .status(CouponStatus.INACTIVE)
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(inactiveDomain);

        assertThatThrownBy(() -> service.redeem(id))
                .isInstanceOf(CouponNotActiveException.class);
    }
}