package com.wolfnine.backend.entity.entityEnum;

public enum DurationType {
    YEAR(1),
    MON(0),
    UNDEFINED(2);
    private int value;

    DurationType (int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static DurationType  of(int value) {
        for (DurationType  DurationType  : DurationType .values()) {
            if(DurationType .getValue() == value) {
                return DurationType ;
            }
        }
        return DurationType .UNDEFINED;
    }
}
