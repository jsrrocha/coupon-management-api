package com.challenge.coupon.repository;

import com.challenge.coupon.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface CouponRepository extends JpaRepository<CouponEntity, UUID> {
}