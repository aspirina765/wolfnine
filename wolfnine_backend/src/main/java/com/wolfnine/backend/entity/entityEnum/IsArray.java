package com.wolfnine.backend.entity.entityEnum;

public enum IsArray {
    ACTIVE(1),
    DEACTIVE(0),
    UNDEFINED(99);
    private int value;

    IsArray (int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static IsArray  of(int value) {
        for (IsArray  IsArray  : IsArray .values()) {
            if(IsArray .getValue() == value) {
                return IsArray ;
            }
        }
        return IsArray .UNDEFINED;
    }
}
