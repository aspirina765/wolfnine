package com.wolfnine.backend.entity.entityEnum;

public enum SelectorType {
    GET_TEXT(1),
    GET_ATTRIBUTE(2),
    GET_HTML_CONTENT(3),
    UNDEFINED(99);
    private int value;

    SelectorType (int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static SelectorType  of(int value) {
        for (SelectorType  SelectorType  : SelectorType .values()) {
            if(SelectorType .getValue() == value) {
                return SelectorType ;
            }
        }
        return SelectorType .UNDEFINED;
    }
}
