package com.challenge.coupon.exception;

public class CouponAlreadyRedeemedException extends BusinessException {
    public CouponAlreadyRedeemedException(String message) {
        super(message);
    }
}