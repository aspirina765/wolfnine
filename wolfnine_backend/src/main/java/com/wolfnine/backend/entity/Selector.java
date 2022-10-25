package com.wolfnine.backend.entity;

import com.wolfnine.backend.entity.entityEnum.CrawlDataType;
import com.wolfnine.backend.entity.entityEnum.IsArray;
import com.wolfnine.backend.entity.entityEnum.SelectorType;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Selector {
    @NotEmpty
    private String key;
    @NotEmpty
    private String selector;
    @NotEmpty
    private int type;
    @NotEmpty
    private String attribute;
    @NotEmpty
    private int isLink;
    @NotEmpty
    private int isArray;
    @NotEmpty
    private int dataType;
}
