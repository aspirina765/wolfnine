package com.wolfnine.backend.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.wolfnine.backend.entity.CrawlCategory;
import com.wolfnine.backend.entity.entityEnum.ProductStatus;
import lombok.*;
import javax.persistence.Column;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private long id;
    private long crawlCategoryId;
    private long categoryId;
    private long userId;
    @JsonRawValue
    private String attributes;
    private String link;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CrawlCategory crawlCategory;
    private ProductStatus status;
//
//    public Object getAttributes() {
//        Gson gson = new Gson();
//        Type type = new TypeToken<Map<String, Object>>() {}.getType();
//        return gson.fromJson(attributesJson, type);
//    }
}
