package com.wolfnine.backend.entity.entityEnum;

public enum PaymentStatus {
    ACTIVE(1),
    DEACTIVE(0),
    DELETED(-1),
    UNDEFINED(2);
    private int value;

    PaymentStatus (int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static PaymentStatus  of(int value) {
        for (PaymentStatus  PaymentStatus  : PaymentStatus .values()) {
            if(PaymentStatus .getValue() == value) {
                return PaymentStatus ;
            }
        }
        return PaymentStatus .UNDEFINED;
    }
}
