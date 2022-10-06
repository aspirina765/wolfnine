package com.wolfnine.backend.entity.entityEnum;

public enum ProductStatus {
    CRAWLED(1),
    CRAWLING(2),
    NOT_CRAWL(0),
    UNDEFINED(99);
    private int value;

    ProductStatus (int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static ProductStatus  of(int value) {
        for (ProductStatus  ProductStatus  : ProductStatus .values()) {
            if(ProductStatus .getValue() == value) {
                return ProductStatus ;
            }
        }
        return ProductStatus .UNDEFINED;
    }
}
