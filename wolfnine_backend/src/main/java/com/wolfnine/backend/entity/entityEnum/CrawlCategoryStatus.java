package com.wolfnine.backend.entity.entityEnum;

public enum CrawlCategoryStatus {
    CRAWLED(1),
    PENDING(0),
    FAILED(-1),
    CRAWLING(3),
    UNDEFINED(2);
    private int value;

    CrawlCategoryStatus (int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static ServiceStatus  of(int value) {
        for (ServiceStatus  ServiceStatus  : ServiceStatus .values()) {
            if(ServiceStatus .getValue() == value) {
                return ServiceStatus ;
            }
        }
        return ServiceStatus .UNDEFINED;
    }
}
