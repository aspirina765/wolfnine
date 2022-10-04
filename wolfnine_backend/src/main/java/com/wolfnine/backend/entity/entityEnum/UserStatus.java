package com.wolfnine.backend.entity.entityEnum;

public enum UserStatus {
    ACTIVE(1),
    DEACTIVE(0),
    DELETED(-1),
    UNDEFINED(2);
    private int value;

    UserStatus (int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static UserStatus  of(int value) {
        for (UserStatus  UserStatus  : UserStatus .values()) {
            if(UserStatus .getValue() == value) {
                return UserStatus ;
            }
        }
        return UserStatus .UNDEFINED;
    }
}
