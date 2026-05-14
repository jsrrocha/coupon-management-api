package com.challenge.coupon.mapper;

import com.challenge.coupon.domain.Coupon;
import com.challenge.coupon.dto.CouponDTO;
import com.challenge.coupon.dto.CouponResponseDTO;
import com.challenge.coupon.entity.CouponEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CouponMapper {
    Coupon toDomain(CouponEntity entity);

    CouponEntity toEntity(Coupon domain);

    CouponResponseDTO toResponseDTO(CouponEntity entity);
}