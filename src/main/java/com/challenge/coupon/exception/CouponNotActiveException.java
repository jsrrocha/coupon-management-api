package com.challenge.coupon.exception;

public class CouponNotActiveException extends BusinessException {
    public CouponNotActiveException(String message) {
        super(message);
    }
}