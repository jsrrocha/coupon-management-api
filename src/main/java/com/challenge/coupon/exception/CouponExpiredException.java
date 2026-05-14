package com.challenge.coupon.exception;

public class CouponExpiredException extends BusinessException {
    public CouponExpiredException(String message) {
        super(message);
    }
}