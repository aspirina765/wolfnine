package com.wolfnine.backend.entity.entityEnum;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.NUMBER)
public enum CrawlConfigTemplateStatus {
    PUBLIC(1),
    PRIVATE(2),
    ONLY_USER(3);

    private int value;

    CrawlConfigTemplateStatus(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return this.value;
    }
}
