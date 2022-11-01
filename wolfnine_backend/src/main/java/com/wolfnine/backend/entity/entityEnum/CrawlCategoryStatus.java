package com.wolfnine.backend.entity.entityEnum;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.NUMBER)
public enum CrawlCategoryStatus {
    PENDING(1),
    CRAWLED(2),
    FAILED(3),
    CRAWLING(4);

    private int value;

    CrawlCategoryStatus(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return this.value;
    }
}
