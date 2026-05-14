package com.challenge.coupon.exception;

import java.util.UUID;

public class CouponAlreadyDeletedException extends BusinessException {
    public CouponAlreadyDeletedException(UUID id) {
        super("O cupom com ID " + id + " já foi excluído anteriormente.");
    }
}