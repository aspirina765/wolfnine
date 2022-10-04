package com.wolfnine.backend.entity.entityEnum;

public enum CrawlCategoryStatus {
    ACTIVE(1),
    DEACTIVE(0),
    DELETED(-1),
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
