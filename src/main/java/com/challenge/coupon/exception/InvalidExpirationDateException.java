package com.challenge.coupon.exception;

public class InvalidExpirationDateException extends BusinessException {
    public InvalidExpirationDateException(String message) {
        super(message);
    }
}