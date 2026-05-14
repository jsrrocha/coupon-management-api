package com.challenge.coupon.exception;

public class CouponDiscountBelowMinimumException extends BusinessException {
    public CouponDiscountBelowMinimumException(String message) {
        super(message);
    }
}