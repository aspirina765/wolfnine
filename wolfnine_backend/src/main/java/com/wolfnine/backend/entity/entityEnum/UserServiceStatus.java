package com.wolfnine.backend.entity.entityEnum;

public enum UserServiceStatus {
    ACTIVE(1),
    DEACTIVE(0),
    DELETED(-1),
    UNDEFINED(2);
    private int value;

    UserServiceStatus (int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static UserServiceStatus  of(int value) {
        for (UserServiceStatus  UserServiceStatus  : UserServiceStatus .values()) {
            if(UserServiceStatus .getValue() == value) {
                return UserServiceStatus ;
            }
        }
        return UserServiceStatus .UNDEFINED;
    }
}
