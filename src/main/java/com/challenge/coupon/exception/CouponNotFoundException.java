package com.challenge.coupon.exception;

import java.util.UUID;

public class CouponNotFoundException extends RuntimeException {
    public CouponNotFoundException(UUID id) {
        super("Cupom com ID " + id + " não foi encontrado.");
    }
}