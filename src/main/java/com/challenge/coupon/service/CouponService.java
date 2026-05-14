package com.challenge.coupon.service;

import com.challenge.coupon.domain.Coupon;
import com.challenge.coupon.dto.CouponResponseDTO;
import com.challenge.coupon.dto.CouponDTO;
import com.challenge.coupon.entity.CouponEntity;
import com.challenge.coupon.entity.CouponStatus;
import com.challenge.coupon.exception.CouponNotFoundException;
import com.challenge.coupon.mapper.CouponMapper;
import com.challenge.coupon.repository.CouponRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository repository;
    private final CouponMapper mapper;

    public CouponResponseDTO create(CouponDTO dto) {
        Coupon domain = Coupon.create(
                dto.code(),
                dto.description(),
                dto.discountValue(),
                dto.expirationDate(),
                dto.status(),
                dto.published()
        );

        CouponEntity entity = mapper.toEntity(domain);

        return mapper.toResponseDTO(repository.save(entity));
    }

    public CouponResponseDTO findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new CouponNotFoundException(id));
    }

    @Transactional
    public void delete(UUID id) {
        CouponEntity entity = repository.findById(id)
                .orElseThrow(() -> new CouponNotFoundException(id));

        Coupon domain = mapper.toDomain(entity);
        domain.validateCanBeDeleted();

        entity.setStatus(CouponStatus.DELETED);
        repository.save(entity);
    }

}