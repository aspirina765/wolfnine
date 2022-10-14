package com.wolfnine.backend.entity;

import com.wolfnine.backend.entity.entityEnum.CrawlDataType;
import com.wolfnine.backend.entity.entityEnum.SelectorType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelectorDetail {
    private String key;
    private String selector;
    private SelectorType type;
    private String attribute;
    private int isLink;
    private CrawlDataType dataType;
}
