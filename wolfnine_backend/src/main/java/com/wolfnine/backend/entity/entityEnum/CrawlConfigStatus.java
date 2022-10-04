package com.wolfnine.backend.entity.entityEnum;

public enum CrawlConfigStatus {
    ACTIVE(1),
    DEACTIVE(0),
    DELETED(-1),
    UNDEFINED(2);
    private int value;

    CrawlConfigStatus (int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static CrawlConfigStatus  of(int value) {
        for (CrawlConfigStatus  CrawlConfigStatus  : CrawlConfigStatus .values()) {
            if(CrawlConfigStatus .getValue() == value) {
                return CrawlConfigStatus ;
            }
        }
        return CrawlConfigStatus .UNDEFINED;
    }
}
