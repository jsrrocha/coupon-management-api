package com.challenge.coupon.exception;

public class InvalidCouponCodeException extends BusinessException {
    public InvalidCouponCodeException(String message) {
        super(message);
    }
}