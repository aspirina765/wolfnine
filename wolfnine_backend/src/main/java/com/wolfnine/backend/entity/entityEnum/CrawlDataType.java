package com.wolfnine.backend.entity.entityEnum;

public enum CrawlDataType {
    TEXT(1),
    IMAGE_LINK(2),
    NORMAL_LINK(3),
    UNDEFINED(99);
    private int value;

    CrawlDataType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static CrawlDataType of(int value) {
        for (CrawlDataType dataType  : CrawlDataType.values()) {
            if(dataType .getValue() == value) {
                return dataType ;
            }
        }
        return CrawlDataType.UNDEFINED;
    }
}
